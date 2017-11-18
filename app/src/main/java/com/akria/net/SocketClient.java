package com.akria.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.akria.domain.Callback;
import com.akria.domain.User;
import com.akria.domain.UserAPI;
import com.henu.utils.SocketUtil;

public class SocketClient {

	private Socket s = SocketUtil.getInstance().getSocket();
	private UserAPI userAPI = new UserAPI();

	// 测试 注册用户
	public void test1() {
		User user = new User();
		user.setUsername("Jack666");
		user.setPassword("123456");
		user.setRealname("Jack");
		user.setStudentid("1510120007");
		user.setPhonenumber("18637680982");
		user.setUsericon("defaulticon.png");

		userAPI.register(user, new Callback() {

			@Override
			public void onSuccess(String msg) {
				System.out.println(msg);
			}

			@Override
			public void onFail(String msg) {
				System.out.println(msg);

			}
		});
	}

	// 测试 查询手机号是否注册
	public void test7() {
//		String phonenumber = "18623885643";
		String phonenumber = "18637885643";
		userAPI.findPhonenumber(phonenumber, new Callback() {

			@Override
			public void onSuccess(String msg) {
				System.out.println(msg);
			}

			@Override
			public void onFail(String msg) {
				System.out.println(msg);

			}
		});
	}

	// 测试 查询用户名是否注册
	public void test6() {
//		String username = "aaaa";
		String username = "Jack666";
		userAPI.findUsername(username, new Callback() {

			@Override
			public void onSuccess(String msg) {
				System.out.println(msg);
			}

			@Override
			public void onFail(String msg) {
				System.out.println(msg);

			}
		});
	}

	// 测试 登录
	public void test2() {
		// String loginname = "18637885643";
//		String password = "666666";
		String password = "123456";
		String loginname = "Jack666";
		userAPI.login(loginname, password, new Callback() {

			@Override
			public void onSuccess(String msg) {
				System.out.println(msg);
			}

			@Override
			public void onFail(String msg) {
				System.out.println(msg);

			}
		});
	}

	// 测试 更改密码
	public void test3() {
		String phonenumber = "18234567890";
		String password = "abcdef";
		userAPI.resetPassword(phonenumber, password, new Callback() {

			@Override
			public void onSuccess(String msg) {
				System.out.println(msg);
			}

			@Override
			public void onFail(String msg) {
				System.out.println(msg);

			}
		});
	}

	// 测试 获取用户信息功能
	public void test4() {
		String phonenumber = "18234567890";
		userAPI.queryUserInfo(phonenumber, new Callback() {

			@Override
			public void onSuccess(String msg) {
				System.out.println(msg);
			}

			@Override
			public void onFail(String msg) {
				System.out.println(msg);

			}
		});
	}

	// 测试 更改用户信息功能
	public void test5() {
		String phonenumber = "18234567890";
		String username = "坏学生b";
		userAPI.changeUserInfo(phonenumber, username ,new Callback() {

			@Override
			public void onSuccess(String msg) {
				System.out.println(msg);
			}

			@Override
			public void onFail(String msg) {
				System.out.println(msg);

			}
		});
	}

	public void test33() {
		Request(null);
		ReciveMeg();
	}

	private void Request(String json) {
		System.out.println(json);
		try {
			PrintWriter out = new PrintWriter(s.getOutputStream());
			out.println(json);
			out.println("over");
			out.flush();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ReciveMeg();
	}

	private void ReciveMeg() {
		InputStream is;
		try {
			is = s.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(is));
			while (true) {
				StringBuilder msg = new StringBuilder();
				String str = null;
				while (!"over".equals(str = in.readLine())) {
					msg.append(str);
				}
				str = msg.toString();
				System.out.println(msg);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
