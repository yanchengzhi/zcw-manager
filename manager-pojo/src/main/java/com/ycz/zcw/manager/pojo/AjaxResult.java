package com.ycz.zcw.manager.pojo;

public class AjaxResult {
	
	private boolean success;
	private Object data;//������װ����

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	
}
