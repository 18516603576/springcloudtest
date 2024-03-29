package com.springcloudtest.oauth2.server.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

/**
 * @author: 林塬
 * @date: 2018/1/15
 * @description: 用户登录数据传输对象
 */
public class LoginDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4861781612750019310L;

	@NotBlank(message = "用户名不能为空")
	private String username;
	@NotBlank(message = "密码不能为空")
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}