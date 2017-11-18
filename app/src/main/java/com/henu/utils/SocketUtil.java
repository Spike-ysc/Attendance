package com.henu.utils;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketUtil {
	private static SocketUtil userUtil;
	private Socket socket;	
	private static String IPAdress = "10.248.117.63";	
	private static final int PORT = 52068;	

	public static void setIPAdress(String ip) {
		IPAdress = ip;
	}

	private SocketUtil() {
		try {
			socket = new Socket(IPAdress, PORT);			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static SocketUtil getInstance() {
		if(userUtil==null) {
			userUtil = new SocketUtil();
		}
		return userUtil;
	}

	public Socket getSocket() {
		return socket;
	}
}
