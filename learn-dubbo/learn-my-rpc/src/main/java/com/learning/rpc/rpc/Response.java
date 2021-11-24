package com.learning.rpc.rpc;

import java.io.Serializable;

/**
 * <p>
 *  服务端响应结果
 *
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/21
 */
public class Response implements Serializable {

    private static final long serialVersionUID = 8586631082577354134L;

    /**
     * 请求中携带的messageId
     */
    private long requestId;

    /**
     * 100：异常
     * 0：正常
     */
    private int status;

    /**
     * 响应内容，方法执行结果，异常信息
     */
    private Object content;

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
