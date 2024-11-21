package com.alibaba.csp.sentinel.dashboard.rule.nacos;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author CHQ
 * @version 1.0
 * @date 2024/5/29
 */
@Component
@ConfigurationProperties(prefix = "sentinel.nacos")
public class NacosProperties {
    // Nacos相关配置
    private String serverAddr;
    private String username;
    private String password;
    private String dataId;
    private String group;
    private String namespace;

    public String getServerAddr() {
        return serverAddr;
    }

    public void setServerAddr(String serverAddr) {
        this.serverAddr = serverAddr;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
