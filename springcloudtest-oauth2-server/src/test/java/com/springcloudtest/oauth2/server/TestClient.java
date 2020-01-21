package com.springcloudtest.oauth2.server;

import java.io.IOException;
import java.util.Map;

import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

/**
 * @author Administrator
 * @version 1.0
 **/
// @SpringBootTest
// @RunWith(SpringRunner.class)
public class TestClient {
	RestTemplate restTemplate = new RestTemplate();

	// 远程请求spring security获取令牌
	@Test
	public void testClient() {

		// 此地址就是http://ip:port
		String uri = "http://127.0.0.1:1501";
		// 令牌申请的地址 http://localhost:40400/auth/oauth/token
		String authUrl = uri + "/oauth/token";
		// 定义header
		LinkedMultiValueMap<String, String> header = new LinkedMultiValueMap<>();
		String httpBasic = getHttpBasic("browser", "secret");
		header.add("Authorization", httpBasic);

		// 定义body
		LinkedMultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("grant_type", "password");
		body.add("username", "fpf");
		body.add("password", "fpf");
		body.add("scope", "ui");

		HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(body, header);
		// String url, HttpMethod method, @Nullable HttpEntity<?> requestEntity,
		// Class<T> responseType, Object... uriVariables

		// 设置restTemplate远程调用时候，对400和401不让报错，正确返回数据
		restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
			@Override
			public void handleError(ClientHttpResponse response) throws IOException {
				if (response.getRawStatusCode() != 400 && response.getRawStatusCode() != 401) {
					super.handleError(response);
				}
			}
		});

		ResponseEntity<Map> exchange = restTemplate.exchange(authUrl, HttpMethod.POST, httpEntity, Map.class);

		// 申请令牌信息
		Map bodyMap = exchange.getBody();
		System.out.println("结果：" + bodyMap);
	}

	// 获取httpbasic的串
	private String getHttpBasic(String clientId, String clientSecret) {
		String string = clientId + ":" + clientSecret;
		// 将串进行base64编码
		byte[] encode = Base64Utils.encode(string.getBytes());
		return "Basic " + new String(encode);
	}

}
