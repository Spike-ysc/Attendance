package com.akria.domain;

public interface Callback {
	public void onSuccess(String msg);

	public void onFail(String msg);
}
