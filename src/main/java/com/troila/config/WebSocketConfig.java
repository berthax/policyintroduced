package com.troila.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.troila.websocket.PolicyInfoHandler;

@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		// TODO Auto-generated method stub
		registry.addHandler(policyInfoHandler(), "/policyInfo");
	}
	
	@Bean
	public PolicyInfoHandler policyInfoHandler() {
		return new PolicyInfoHandler();
	}
	
}
