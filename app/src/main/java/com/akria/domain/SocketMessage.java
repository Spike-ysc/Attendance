package com.akria.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by Akria on 2017/10/5.
 * 模拟数据模型，用于客户端与服务端的传输
 */
public class SocketMessage implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 定义消息类型常量
	 * TYPE_HEART_BEAT  
	 * TYPE_EVENT		事件消息
	 * TYPE_ERROR		错误消息
	 * TYPE_EXIT		退出请求
	 */
	public static final int TYPE_HEART_BEAT = 0;
	public static final int TYPE_EVENT = 1;
	public static final int TYPE_ERROR = 3;
	public static final int TYPE_EXIT = 4;
	
	/**
	 * 定义消息信息常量
	 */
	
	public static final int UN_EXIST = 11;
	public static final int UN_NOT_EXIST = 12;
	public static final int PN_EXIST = 13;
	public static final int PN_NOT_EXIST = 14;
	public static final int LOGIN_FAILED = 15;
	public static final int LOGIN_SUC = 16;
	public static final int USER_NOT_EXIST = 17;
	public static final int USER_EXIST = 18;
	public static final int RESET_FAILED = 19;
	public static final int RESET_SUCC = 20;
	public static final int REGISTER_FAILED = 21;
	public static final int REGISTER_SUC = 22;
	

	private static Map<Integer, String> Messages = new HashMap<Integer, String>();
	
	public static Map<Integer, String> getMessages() {
		return Messages;
	}

	static {
		Messages.put(UN_EXIST, "用户名已存在！");
		Messages.put(UN_NOT_EXIST, "用户名不存在！");
		Messages.put(PN_EXIST, "手机号已被注册！");
		Messages.put(PN_NOT_EXIST, "手机号未注册！");
		Messages.put(LOGIN_FAILED, "登录失败！");
		Messages.put(LOGIN_SUC, "登录成功！");
		Messages.put(USER_NOT_EXIST, "用户不存在！");
		Messages.put(USER_EXIST, "用户已存在！");
		Messages.put(RESET_FAILED, "修改失败！");
		Messages.put(RESET_SUCC, "修改成功！");
		Messages.put(REGISTER_FAILED, "注册失败！");
		Messages.put(REGISTER_SUC, "注册成功！");
	}
	
	/**
	 * 消息类型
	 */
	private int type;
	/**
	 * 消息内容
	 */
    private String message;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String toJsonString() {
        return JSON.toJSONString(this);
    }
    
    public static SocketMessage parseJson(String json) {
		SocketMessage message = new SocketMessage();
		try {
			JSONObject jsonObject = JSONObject.parseObject(json);
			message.setType(jsonObject.getIntValue("type"));			
			message.setMessage(jsonObject.getString("message"));			
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
		return message;
	}
}

