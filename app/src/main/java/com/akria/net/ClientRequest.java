package com.akria.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import com.akria.domain.SocketMessage;
import com.alibaba.fastjson.JSON;
import com.henu.utils.SocketUtil;

public class ClientRequest {
	private SocketMessage request;
	private Socket socket = SocketUtil.getInstance().getSocket();

	public ClientRequest(SocketMessage request) {
		this.request = request;
	}

	public void sendRequest() {
		SendMsgHandler handler = new SendMsgHandler(socket, JSON.toJSONString(request));
		Thread t = new Thread(handler);
		t.start();
	}

	public SocketMessage ReceiveMessage() {		
		SocketMessage sm = null;
		try {
			InputStream is = socket.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(is));

			StringBuilder sb = new StringBuilder();
			String msg = null;
			while (!"over".equals(msg = in.readLine())) {
				sb.append(msg);
			}
			msg = sb.toString();
			sm = SocketMessage.parseJson(msg);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return sm;
	}
}
