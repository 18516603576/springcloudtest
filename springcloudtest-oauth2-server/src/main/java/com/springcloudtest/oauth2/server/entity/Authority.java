package com.springcloudtest.oauth2.server.entity;

import java.io.Serializable;

/**
 * Created by SuperS on 2017/9/25.
 *
 * @author SuperS
 */

public class Authority implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2864024410186580551L;

	private Long id;
	private String name;
	private String value;

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

}
