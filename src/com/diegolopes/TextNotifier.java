package com.diegolopes;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/sendText", name = "sendTextMessage")
public class TextNotifier extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		final String body = request.getReader().lines().collect(Collectors.joining("\n"));
		if (body.isEmpty()) {
			response.setStatus(500);
			return;
		}
		WebSocketServer.broadcast(body);
		response.setStatus(200);
	}

}
