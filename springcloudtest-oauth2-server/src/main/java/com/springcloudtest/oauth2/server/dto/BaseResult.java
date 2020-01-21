/**
 * 
 */
package com.springcloudtest.oauth2.server.dto;

import java.io.Serializable;

/**
 * 返回来的结果 用于接口和服务调用的情况
 * 
 * @author zhuangchao
 * @date 2017-6-16
 * 
 */
public class BaseResult<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5275597785896701011L;
	/**
	 * 异常码.可以是业务异常码，也可以是系统异常码
	 */
	private int errorCode;

	/**
	 * 结果码 成功 失败
	 */
	private Boolean result = false;

	/**
	 * 信息 如果失败的话 ，就把失败信息丢这个里面
	 */
	private String message;

	/**
	 * 返回来的对象
	 */
	private T model;

	/**
	 * 对象字段名
	 */
	private String[] field;

	/**
	 * @return the result
	 */
	public Boolean getResult() {
		return result;
	}

	/**
	 * @param result
	 *            the result to set
	 */
	public void setResult(Boolean result) {
		this.result = result;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the model
	 */
	public T getModel() {
		return model;
	}

	/**
	 * @param model
	 *            the model to set
	 */
	public void setModel(T model) {
		this.model = model;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String[] getField() {
		return field;
	}

	public void setField(String[] field) {
		this.field = field;
	}

}
