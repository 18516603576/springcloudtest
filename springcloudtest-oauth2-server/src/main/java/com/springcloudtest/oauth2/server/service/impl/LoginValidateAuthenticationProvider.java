package com.springcloudtest.oauth2.server.service.impl;

import javax.annotation.Resource;

import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class LoginValidateAuthenticationProvider implements AuthenticationProvider {

	@Resource
	private UserDetailsService jpaUserDetailsService;

	// 解密用的
	@Resource
	private PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// 获取输入的用户名
		String username = authentication.getName();
		// 获取输入的明文
		String rawPassword = (String) authentication.getCredentials();

		// 查询用户是否存在

		UserDetails user = jpaUserDetailsService.loadUserByUsername(username);

		if (!user.isEnabled()) {
			throw new DisabledException("该账户已被禁用，请联系管理员");

		} else if (!user.isAccountNonLocked()) {
			throw new LockedException("该账号已被锁定");

		} else if (!user.isAccountNonExpired()) {
			throw new AccountExpiredException("该账号已过期，请联系管理员");

		} else if (!user.isCredentialsNonExpired()) {
			throw new CredentialsExpiredException("该账户的登录凭证已过期，请重新登录");
		}

		// 验证密码
		if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
			throw new BadCredentialsException("输入密码错误!");
		}

		return new UsernamePasswordAuthenticationToken(user, rawPassword, user.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// 确保authentication能转成该类
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
