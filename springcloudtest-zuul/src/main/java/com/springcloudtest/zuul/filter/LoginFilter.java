package com.springcloudtest.zuul.filter;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.springcloudtest.zuul.service.impl.AuthService;

@Component
public class LoginFilter extends ZuulFilter {
	@Autowired
	AuthService authService;

	@Override
	public boolean shouldFilter() {
		// 返回true表示要执行此过虑器
		return true;
	}

	@Override
	public String filterType() {
		/**
		 * pre：请求在被路由之前执行
		 * 
		 * routing：在路由请求时调用
		 * 
		 * post：在routing和errror过滤器之后调用
		 * 
		 * error：处理请求时发生错误调用
		 * 
		 */
		return "pre";
	}

	@Override
	public int filterOrder() {
		// 过虑器序号，越小越被优先执行
		return 0;
	}

	@Override
	public Object run() throws ZuulException {
		RequestContext requestContext = RequestContext.getCurrentContext();
		// 得到request
		HttpServletRequest request = requestContext.getRequest();
		// 得到response
		HttpServletResponse response = requestContext.getResponse();

		// 1、从header中取token
		String token = authService.getTokenFromHeader(request);
		if (StringUtils.isEmpty(token)) {
			// 拒绝访问
			access_denied();
			return null;
		}
		// 2、从redis取出token的过期时间
		long expire = authService.getExpire(token);
		if (expire < 0) {
			// 拒绝访问
			access_denied();
			return null;
		}

		return null;
	}

	// 拒绝访问
	private void access_denied() {
		RequestContext requestContext = RequestContext.getCurrentContext();
		// 得到response
		HttpServletResponse response = requestContext.getResponse();
		// zuul拒绝访问
		requestContext.setSendZuulResponse(false);
		// 设置响应代码
		requestContext.setResponseStatusCode(200);
		// 构建响应的信息

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("error_code", 401);
		map.put("message", "用户未授权");
		map.put("result", false);

		// 转成json
		String jsonString = JSON.toJSONString(map);
		requestContext.setResponseBody(jsonString);
		// 转成json，设置contentType
		response.setContentType("application/json;charset=utf-8");
	}

}
