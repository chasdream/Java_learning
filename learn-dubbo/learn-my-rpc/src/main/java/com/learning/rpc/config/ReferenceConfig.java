package com.learning.rpc.config;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  引用相关配置
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2021/8/22
 */
public class ReferenceConfig {

    private List<RegistryConfig> registryConfigs;

    private List<ProtocolConfig> protocolConfigs;

    private Class<?> service;

    private String version;

    private String loadBalance;

    public synchronized void addRegistryConfigs(RegistryConfig registryConfig) {
        if (registryConfigs == null) {
            registryConfigs = new ArrayList<>();
        }
        registryConfigs.add(registryConfig);
    }

    public synchronized void addProtocolConfigs(ProtocolConfig protocolConfig) {
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getLoadBalance() {
        return loadBalance;
    }

    public void setLoadBalance(String loadBalance) {
        this.loadBalance = loadBalance;
    }
}
