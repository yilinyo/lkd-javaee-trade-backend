package com.yilin.trade.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.websocket.server.ServerEndpoint;

/**
 * Title: WebSocketConfig
 * Description: TODO
 * WebSocket配置类
 * @author Yilin
 * @version V1.0
 * @date 2022-11-13
 */
@Configuration

public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }
}

