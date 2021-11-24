package com.learning.rpc.rpc.protocol.rpc.codec;

import com.learning.rpc.common.serialize.Serialization;
import com.learning.rpc.common.tools.ByteUtils;
import com.learning.rpc.remoting.Codec;
import com.learning.rpc.rpc.RPCInvocation;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * rpc编解码器
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/21
 */
public class RPCCodec implements Codec {

    /**
     * 请求的特殊开头
     */
    public final static byte[] MAGIC = new byte[]{(byte) 0xda, (byte) 0xbb};

    /**
     * 协议头部长度
     */
    public static final int HEADER_LEN = 6;

    /**
     * 临时保存没有处理过的请求报文
     */
    ByteBuf tempMsg = Unpooled.buffer();

    /**
     * 反序列化的对象
     */
    private Class<?> decodeType;

    /**
     * 序列化
     */
    private Serialization serialization;

    public void setDecodeType(Class<?> decodeType) {
        this.decodeType = decodeType;
    }

    public Serialization getSerialization() {
        return serialization;
    }

    public void setSerialization(Serialization serialization) {
        this.serialization = serialization;
    }

    /**
     * 调用端：编码序列化
     *
     * 客户端：编码 rpcInvocation
     * 服务端：编码 response
     *
     * @param msg
     * @return
     * @throws Exception
     */
    @Override
    public byte[] encode(Object msg) throws Exception {
        byte[] responseBody = (byte[]) msg;
        // 构建header(协议头)
        ByteBuf requestByteBuf = Unpooled.buffer();
        requestByteBuf.writeByte(0xda);
        requestByteBuf.writeByte(0xbb);
        requestByteBuf.writeBytes(ByteUtils.int2bytes(responseBody.length));
        requestByteBuf.writeBytes(responseBody);

        byte[] result = new byte[requestByteBuf.readableBytes()];
        requestByteBuf.readBytes(result);
        return result;
    }

    /**
     * 服务端：解码的结果是 {@link RPCInvocation} 对象集合
     *
     * 客户端：解码 response
     * 服务端：解码 rpcInvocation
     *
     * @param data
     * @return
     * @throws Exception
     */
    @Override
    public List<Object> decode(byte[] data) throws Exception {

        List<Object> out = new ArrayList<>();

        // 解析：解析头部、取出数据、封装成invocation
        // 合并报文
        ByteBuf message = Unpooled.buffer();
        int tmpMsgSize = tempMsg.readableBytes();
        // 如果暂存有上一次余下的请求报文，则合并（粘包过程）
        if (tmpMsgSize > 0) {
            message.writeBytes(tempMsg);
            message.writeBytes(data);
            System.out.println("合并：上一数据包余下的长度为：" + tmpMsgSize + "合并后的长度为：" + message.readableBytes());
        } else {
            message.writeBytes(data);
        }

        for (; ; ) {
            // 如果数据太少，不够一个头部，待会处理
            if (HEADER_LEN >= message.readableBytes()) {
                tempMsg.clear();
                tempMsg.writeBytes(message);
                return out;
            }

            // 解析数据
            // 检查关键字
            byte[] magic = new byte[2];
            message.readBytes(magic);
            for (; ; ) {
                // 如果不符合关键字，则一直读取到有正常关键字为止
                if (magic[0] != MAGIC[0] || magic[1] != MAGIC[1]) {
                    if (message.readableBytes() == 0) {
                        // 所有数据读完都没有正确的头，则返回，等下次数据
                        tempMsg.clear();
                        tempMsg.writeByte(magic[1]);
                        return out;
                    }
                    magic[0] = magic[1]; // 如果比对字节不是想要的字节，则将前一个字节丢掉
                    magic[1] = message.readByte(); // 每次读取下一个字节
                } else {
                    break;
                }
            }

            // 读取剩余数据的长度
            byte[] lengthBytes = new byte[4];
            message.readBytes(lengthBytes);

            int length = ByteUtils.bytes2Int(lengthBytes);

            // 读取body
            // 如果body没有传输完，临时存放，先不处理
            if (message.readableBytes() < length) {
                tempMsg.clear();
                tempMsg.writeBytes(magic);
                tempMsg.writeBytes(lengthBytes);
                tempMsg.writeBytes(message);
                return out;
            }

            byte[] body = new byte[length];
            message.readBytes(body);
            // 序列化
            Object o = getSerialization().deserialize(body, decodeType);
            out.add(o);
        }
    }

    @Override
    public Codec createInstance() {
        RPCCodec rpcCodec = new RPCCodec();
        rpcCodec.setDecodeType(this.decodeType);
        rpcCodec.setSerialization(this.serialization);
        return rpcCodec;
    }
}
