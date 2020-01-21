package com.springcloudtest.zuul.service.impl;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 * @version 1.0
 **/
@Service
public class AuthService {

	@Autowired
	StringRedisTemplate stringRedisTemplate;

	// 从头取出jwt令牌
	public String getTokenFromHeader(HttpServletRequest request) {
		// 取出头信息
		String authorization = request.getHeader("Authorization");
		if (StringUtils.isBlank(authorization)) {
			return null;
		}
		if (!authorization.startsWith("bearer ")) {
			return null;
		}
		// 取到jwt令牌
		String jwt = authorization.substring(7);
		return jwt;

	}

	// 查询令牌的有效期
	public long getExpire(String access_token) {
		// key
		String key = "access:" + access_token;
		Long expire = stringRedisTemplate.getExpire(key, TimeUnit.SECONDS);
		return expire;
	}
}
