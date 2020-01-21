package com.springcloudtest.oauth2.server.config;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.springcloudtest.oauth2.server.service.impl.JPAUserDetailsService;
import com.springcloudtest.oauth2.server.service.impl.LoginValidateAuthenticationProvider;

/**
 * Created by SuperS on 2017/9/25.
 *
 * @author SuperS
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private JPAUserDetailsService jpaUserDetailsService;
	@Resource
	private LoginValidateAuthenticationProvider loginValidateAuthenticationProvider;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http.authorizeRequests().antMatchers("/oauth/*", "/login1").permitAll()//
				.anyRequest().authenticated().and().csrf().disable();
		// @formatter:on
		// http.csrf().disable().httpBasic().and().formLogin().and().authorizeRequests().anyRequest().authenticated();

	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(jpaUserDetailsService).passwordEncoder(passwordEncoder());
		// auth.authenticationProvider(loginValidateAuthenticationProvider);
	}

	// 不定义没有password grant_type
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter() throws Exception {
		UsernamePasswordAuthenticationFilter filter = new UsernamePasswordAuthenticationFilter();
		filter.setAuthenticationManager(authenticationManagerBean());
		return filter;
	}

	// @Override
	// public void configure(WebSecurity web) throws Exception {
	// web.ignoring().antMatchers("/login", "/logout");
	//
	// }
}
