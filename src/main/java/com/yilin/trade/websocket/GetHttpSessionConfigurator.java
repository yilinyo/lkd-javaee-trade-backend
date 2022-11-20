package com.yilin.trade.websocket;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

/**
 * Title: GetHttpSessionConfigurator
 * Description: TODO
 * 获取httpsession
 * @author Yilin
 * @version V1.0
 * @date 2022-11-13
 */
public class GetHttpSessionConfigurator extends ServerEndpointConfig.Configurator {

    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        HttpSession httpSession = (HttpSession)request.getHttpSession();

        //将httpsession存到配置对象

        sec.getUserProperties().put(HttpSession.class.getName(),httpSession);
    }
}
