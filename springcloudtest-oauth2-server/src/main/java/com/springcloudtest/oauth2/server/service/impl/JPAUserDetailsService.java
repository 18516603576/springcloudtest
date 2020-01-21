package com.springcloudtest.oauth2.server.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.springcloudtest.oauth2.server.dao.UserDao;
import com.springcloudtest.oauth2.server.entity.User;

/**
 * Created by SuperS on 2017/9/25.
 *
 * @author SuperS
 */
@Service("jpaUserDetailsService")
public class JPAUserDetailsService implements UserDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(JPAUserDetailsService.class);
	@Autowired
	private UserDao userDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.getByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}

		// 数据库中取出的数据加密
		String password = new BCryptPasswordEncoder().encode(user.getPassword());
		user.setPassword(password);

		return user;
	}

	public User getByUsername(String username) {
		// TODO Auto-generated method stub
		User user = userDao.getByUsername(username);

		return user;
	}
}
