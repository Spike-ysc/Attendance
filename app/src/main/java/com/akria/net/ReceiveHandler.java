package com.akria.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import com.henu.utils.SocketUtil;

public class ReceiveHandler implements Runnable {

	@Override
	public void run() {
		try {
			Socket s = SocketUtil.getInstance().getSocket();
			InputStream is = s.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(is));
			boolean flag = true;			
			while(flag) {
				StringBuilder msg = new StringBuilder();
				String str = null;
				while (!"over".equals(str=in.readLine())) {					
					msg.append(str);
					}
				str = msg.toString();
//				SocketMessage sm = SocketMessage.parseJson(str);
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
}
