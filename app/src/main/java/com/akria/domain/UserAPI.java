package com.akria.domain;

import com.akria.net.ClientRequest;
import com.alibaba.fastjson.JSONObject;
import com.henu.utils.UserUtil;

public class UserAPI {
	public void register(User user, Callback callback) {
		SocketMessage sm = new SocketMessage();
		sm.setType(SocketMessage.TYPE_EVENT);
		JSONObject json = (JSONObject) JSONObject.toJSON(user);
		//对密码加密
		String password = json.getString("password");
		json.put("password", UserUtil.md5(password));		
		json.put("ID", "001");
		sm.setMessage(json.toJSONString());
		Request(sm, callback);
	}
	
	public void login(String loginname, String password, Callback callback) {
		SocketMessage sm = new SocketMessage();
		sm.setType(SocketMessage.TYPE_EVENT);
		JSONObject json = new JSONObject();
		json.put("ID", "002");
		json.put("loginname", loginname);
		json.put("password", UserUtil.md5(password));
		sm.setMessage(json.toJSONString());
		Request(sm, callback);
	}
	
	public void resetPassword(String phonenumber, String password, Callback callback) {
		SocketMessage sm = new SocketMessage();
		sm.setType(SocketMessage.TYPE_EVENT);
		JSONObject json = new JSONObject();
		json.put("ID", "003");
		json.put("phonenumber", phonenumber);
		json.put("password", UserUtil.md5(password));
		sm.setMessage(json.toJSONString());
		Request(sm, callback);
	}
	
	public void queryUserInfo(String phonenumber, Callback callback) {
		SocketMessage sm = new SocketMessage();
		sm.setType(SocketMessage.TYPE_EVENT);
		JSONObject json = new JSONObject();
		json.put("ID", "004");
		json.put("phonenumber", phonenumber);
		sm.setMessage(json.toJSONString());
		Request(sm, callback);
	}
	
	public void changeUserInfo(String phonenumber, String username, Callback callback) {
		SocketMessage sm = new SocketMessage();
		sm.setType(SocketMessage.TYPE_EVENT);
		JSONObject json = new JSONObject();
		json.put("ID", "005");
		json.put("phonenumber", phonenumber);
		json.put("username", username);
		sm.setMessage(json.toJSONString());
		Request(sm, callback);
	}
	
	public void findUsername(String username, Callback callback) {
		SocketMessage sm = new SocketMessage();
		sm.setType(SocketMessage.TYPE_EVENT);
		JSONObject json = new JSONObject();
		json.put("ID", "006");
		json.put("username", username);
		sm.setMessage(json.toJSONString());
		Request(sm, callback);
	}
	
	public void findPhonenumber(String phonenumber, Callback callback) {
		SocketMessage sm = new SocketMessage();
		sm.setType(SocketMessage.TYPE_EVENT);
		JSONObject json = new JSONObject();
		json.put("ID", "007");
		json.put("phonenumber", phonenumber);
		sm.setMessage(json.toJSONString());
		Request(sm, callback);
	}	
	
	private void Request(SocketMessage sm, Callback callback) {
		ClientRequest request = new ClientRequest(sm);
		request.sendRequest();
		SocketMessage message = request.ReceiveMessage();
		if(SocketMessage.TYPE_EVENT == message.getType()) {
			callback.onSuccess(message.getMessage());
		}else if(SocketMessage.TYPE_ERROR == message.getType()) {
			callback.onFail(message.getMessage());
		}
	}	
}
