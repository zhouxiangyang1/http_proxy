package com.demo.it.proxy.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = ProxyServerProperties.prefix)
public class ProxyServerProperties {

    public final static String prefix = "proxy.server";

    private Integer port;

    private final Tcp tcp = new Tcp();

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Tcp getTcp() {
        return tcp;
    }

    public static class Tcp {

        private Boolean enable;

        private String remoteHost;

        private Integer remotePort;

        public Boolean getEnable() {
            return enable;
        }

        public void setEnable(Boolean enable) {
            this.enable = enable;
        }

        public String getRemoteHost() {
            return remoteHost;
        }

        public void setRemoteHost(String remoteHost) {
            this.remoteHost = remoteHost;
        }

        public Integer getRemotePort() {
            return remotePort;
        }

        public void setRemotePort(Integer remotePort) {
            this.remotePort = remotePort;
        }
    }


}
