package com.springcloudtest.oauth2.server.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by SuperS on 2017/9/25.
 *
 * @author SuperS
 */

public class Role implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private String value;

	private Set<Authority> authorities = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Set<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
	}

}
