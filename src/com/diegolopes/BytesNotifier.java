package com.diegolopes;

import java.io.IOException;
import java.nio.charset.Charset;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

@WebServlet(urlPatterns = "/sendBytes", name = "sendBytesMessage")
public class BytesNotifier extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		final byte[] data = IOUtils.toByteArray(request.getReader(), Charset.forName("UTF-8"));
		if (data.length == 0) {
			response.setStatus(500);
			return;
		}
		WebSocketServer.broadcastBytes(data);
		response.setStatus(200);
	}

}
