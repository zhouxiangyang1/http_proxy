package com.demo.it.proxy;

import com.demo.it.proxy.netty.NetworkProxyServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.util.List;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@Slf4j
public class NetworkProxyApplication implements CommandLineRunner {

    private final List<NetworkProxyServer> proxyServers;

    public NetworkProxyApplication(List<NetworkProxyServer> proxyServers) {
        this.proxyServers = proxyServers;
    }


    public static void main(String[] args) {
        SpringApplication.run(NetworkProxyApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        for (NetworkProxyServer proxyServer : proxyServers) {
            proxyServer.start();
        }
    }
}
