package com.learning.rpc.config;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/21
 */
public class ServiceConfig {

    /**
     * 注册信息配置
     */
    private List<RegistryConfig> registryConfigs;

    /**
     * 协议配置
     */
    private List<ProtocolConfig> protocolConfigs;

    private Class<?> service;

    private Object reference;

    private String version;

    public synchronized void addRegistryConfig(RegistryConfig registryConfig) {
        if (registryConfigs == null) {
            registryConfigs = new ArrayList<>();
        }
        registryConfigs.add(registryConfig);
    }

    public synchronized void addProtocolConfig(ProtocolConfig protocolConfig) {
        if (protocolConfigs == null) {
            protocolConfigs = new ArrayList<>();
        }
        protocolConfigs.add(protocolConfig);
    }

    public List<RegistryConfig> getRegistryConfigs() {
        return registryConfigs;
    }

    public void setRegistryConfigs(List<RegistryConfig> registryConfigs) {
        this.registryConfigs = registryConfigs;
    }

    public List<ProtocolConfig> getProtocolConfigs() {
        return protocolConfigs;
    }

    public void setProtocolConfigs(List<ProtocolConfig> protocolConfigs) {
        this.protocolConfigs = protocolConfigs;
    }

    public Class<?> getService() {
        return service;
    }

    public void setService(Class<?> service) {
        this.service = service;
    }

    public Object getReference() {
        return reference;
    }

    public void setReference(Object reference) {
        this.reference = reference;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
