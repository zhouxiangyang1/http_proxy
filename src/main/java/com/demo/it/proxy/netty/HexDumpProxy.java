
/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.demo.it.proxy.netty;

import com.demo.it.proxy.netty.handler.HexDumpProxyFrontendHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public final class HexDumpProxy implements NetworkProxyServer {

    private final int serverPort;

    private final String remoteHost;

    private final int remotePort;


    public HexDumpProxy(int serverPort, String remoteHost, int remotePort) {
        this.serverPort = serverPort;
        this.remoteHost = remoteHost;
        this.remotePort = remotePort;
    }

    @Override
    public void start() throws Exception {
        // Configure the bootstrap.
        log.info("Proxying *:" + serverPort + " to " + remoteHost + ':' + remotePort + " ...");
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(
                                    new LoggingHandler(LogLevel.INFO),
                                    new HexDumpProxyFrontendHandler(remoteHost, remotePort));
                        }
                    })
                    .childOption(ChannelOption.AUTO_READ, false)
                    .bind(serverPort).sync().channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}