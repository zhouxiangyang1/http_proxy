package com.demo.it.proxy.config;

import com.demo.it.proxy.config.properties.ProxyServerProperties;
import com.demo.it.proxy.netty.HexDumpProxy;
import com.demo.it.proxy.netty.NetworkProxyServer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = {ProxyServerProperties.class})
public class NetworkProxyAutoConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = ProxyServerProperties.prefix, value = "tcp.enabled", havingValue = "true")
    public NetworkProxyServer hexDumpProxy(ProxyServerProperties proxyServerProperties) {
        return new HexDumpProxy(proxyServerProperties.getPort(),
                proxyServerProperties.getTcp().getRemoteHost(),
                proxyServerProperties.getTcp().getRemotePort());
    }

}
