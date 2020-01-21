package com.springcloudtest.oauth2.server.config;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import com.springcloudtest.oauth2.server.service.impl.JPAUserDetailsService;

/**
 * Created by SuperS on 2017/9/25.
 *
 * @author SuperS
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	@Resource
	private AuthenticationManager authenticationManager;
	@Resource
	private RedisConnectionFactory connectionFactory;
	@Resource
	private JPAUserDetailsService jpaUserDetailsService;

	@Bean
	public RedisTokenStore tokenStore() {
		return new RedisTokenStore(connectionFactory);
	}

	// 加密方式
	// @Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.authenticationManager(authenticationManager)//
				.userDetailsService(jpaUserDetailsService)//
				.tokenStore(tokenStore());
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.allowFormAuthenticationForClients()//
				// .passwordEncoder(new BCryptPasswordEncoder())//
				.tokenKeyAccess("permitAll()")//
				.checkTokenAccess("isAuthenticated()");
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()//
				.withClient("browser").secret(passwordEncoder().encode("secret"))//
				.authorizedGrantTypes("refresh_token", "password").scopes("ui");
	}
}
