package com.springcloudtest.oauth2.server.controller;

import java.util.Base64;
import java.util.Collections;
import java.util.Map;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.springcloudtest.oauth2.server.dto.BaseResult;
import com.springcloudtest.oauth2.server.dto.LoginDto;
import com.springcloudtest.oauth2.server.dto.UserDto;
import com.springcloudtest.oauth2.server.entity.User;
import com.springcloudtest.oauth2.server.service.impl.JPAUserDetailsService;
import com.springcloudtest.oauth2.server.util.ValidUtil;

/**
 * @author: 林塬
 * @date: 2018/1/16
 * @description: 令牌管理接口
 */
@RestController
@RequestMapping(value = "/", produces = "application/json;charset=UTF-8")
public class LoginController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Resource
	private RestTemplate restTemplate;
	@Resource
	private JPAUserDetailsService jpaUserService;

	/**
	 * 通过密码授权方式向授权服务器获取令牌
	 * 
	 * @param entity
	 * @param bindingResult
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/login1")
	public Object login(@Valid LoginDto entity, BindingResult bindingResult) {

		// String md5Password = DigestUtils.md5Hex(md5key + entity.getPassword());
		String md5Password = entity.getPassword();

		// 1.基本验证
		BaseResult<UserDto> baseResult = new BaseResult<UserDto>();
		Map<String, String> map = ValidUtil.error2map(bindingResult);
		if (map.size() > 0) {
			baseResult.setResult(false);
			baseResult.setMessage(map.toString());
			return baseResult;
		}

		// 2.获取token
		// Http Basic 验证
		// String clientAndSecret = oAuth2ClientProperties.getClientId() + ":" +
		// oAuth2ClientProperties.getClientSecret();
		String clientAndSecret = "browser:secret";
		// 这里需要注意为 Basic 而非 Bearer
		clientAndSecret = "Basic " + Base64.getEncoder().encodeToString(clientAndSecret.getBytes());
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("Authorization", clientAndSecret);
		// 授权请求信息
		MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
		param.put("username", Collections.singletonList(entity.getUsername()));
		param.put("password", Collections.singletonList(md5Password));
		param.put("grant_type", Collections.singletonList("password"));
		param.put("scope", Collections.singletonList("ui"));
		// HttpEntity
		HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<MultiValueMap<String, String>>(param,
				httpHeaders);
		// 获取 Token
		// String uri = "http://localhost:1501/oauth/token";

		// 此地址就是http://ip:port
		String uri = "http://127.0.0.1:1501/oauth/token";

		try {
			Object access_token = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, Object.class).getBody();
			User user = jpaUserService.getByUsername(entity.getUsername());
			UserDto dto = new UserDto();
			dto.setId(user.getId());
			dto.setUpdateTime(user.getUpdateTime());
			dto.setCreateTime(user.getCreateTime());
			dto.setEmail(user.getEmail());
			dto.setFirstName(user.getFirstName());
			dto.setImageUrl(user.getImageUrl());
			dto.setLastName(user.getLastName());
			// dto.setPassword(user.getPassword());
			dto.setUsername(user.getUsername());
			dto.setToken(access_token);

			baseResult.setResult(true);
			baseResult.setMessage("登录成功");
			baseResult.setModel(dto);
		} catch (HttpClientErrorException e) {
			int httpStatus = e.getStatusCode().value();
			if (httpStatus == 401) {
				baseResult.setResult(false);
				baseResult.setErrorCode(401);
				baseResult.setMessage("用户不存在401");
			} else if (httpStatus == 400) {
				baseResult.setResult(false);
				baseResult.setErrorCode(400);
				baseResult.setMessage("请求失败，请检查密码是否正确400");
			} else {
				baseResult.setResult(false);
				baseResult.setErrorCode(500);
				baseResult.setMessage("未知错误：" + e.getMessage());
			}
		}

		String result = JSONObject.toJSONString(baseResult, SerializerFeature.WriteNullStringAsEmpty,
				SerializerFeature.WriteNullListAsEmpty);
		logger.info("login:" + result);
		return result;
		// return baseResult;

	}

}
