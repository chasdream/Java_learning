package com.learning.rpc.config;

/**
 * <p>
 *  协议相关配置
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/21
 */
public class ProtocolConfig {

    private String name;

    private String port;

    private String host;

    private String serialization;

    /**
     * 选择网络底层框架实现
     */
    private String transporter;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getSerialization() {
        return serialization;
    }

    public void setSerialization(String serialization) {
        this.serialization = serialization;
    }

    public String getTransporter() {
        return transporter;
    }

    public void setTransporter(String transporter) {
        this.transporter = transporter;
    }
}
