package com.springcloudtest.oauth2.server.dto;

import com.springcloudtest.oauth2.server.entity.User;

public class UserDto extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3136192050464635005L;

	/*
	 * 访问令牌
	 */
	private Object token;

	public Object getToken() {
		return token;
	}

	public void setToken(Object token) {
		this.token = token;
	}

}
