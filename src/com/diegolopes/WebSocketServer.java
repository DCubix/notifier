package com.diegolopes;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/ws")
public class WebSocketServer {
	
	private static final List<Session> clients = new ArrayList<>();

	@OnOpen
	public void onOpen(Session user) {
		clients.add(user);
	}

	@OnClose
	public void onClose(Session user) {
		clients.remove(user);
	}
	
	@OnError
	public void onError(Throwable e){
		e.printStackTrace();
	}

	public static void broadcast(String message) {
		for (Session client : clients) {
			if (client != null && client.isOpen()) {
				try {
					client.getBasicRemote().sendText(message);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void broadcastBytes(byte[] data) {
		final ByteBuffer buf = ByteBuffer.wrap(data);
		for (Session client : clients) {
			if (client != null && client.isOpen()) {
				try {
					client.getBasicRemote().sendBinary(buf);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
