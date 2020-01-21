package com.springcloudtest.oauth2.server.dao;

import org.apache.ibatis.annotations.Mapper;

import com.springcloudtest.oauth2.server.entity.User;

/**
 * Created by SuperS on 2017/9/25.
 *
 * @author SuperS
 */
@Mapper
public interface UserDao {
	User getByUsername(String username);
}
