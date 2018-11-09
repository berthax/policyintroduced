package com.troila.websocket;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;
/**
 * 后期可能用于向服务器推送更新消息,待完善
 * @author xuanguojing
 *
 */
public class PolicyInfoHandler extends AbstractWebSocketHandler{
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		// socket连接建立后
		super.afterConnectionEstablished(session);
		System.out.println("连接已经建立，可以开始发送消息了……");
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		// 处理消息
		super.handleMessage(session, message);
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		// 处理文本消息
		super.handleTextMessage(session, message);
		System.out.println("接收到消息： "+message.getPayload());
		Thread.sleep(2000);
		session.sendMessage(new TextMessage("Lucy Lili"));
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		// 连接关闭时
		super.afterConnectionClosed(session, status);
		System.out.println("连接已经关闭，状态为： "+status);
	}	
}
