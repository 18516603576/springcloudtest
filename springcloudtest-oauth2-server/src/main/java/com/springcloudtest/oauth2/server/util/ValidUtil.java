package com.springcloudtest.oauth2.server.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class ValidUtil {

	public static String getAllErrorsMsg(BindingResult bindingResult) {
		StringBuilder errorMsg = new StringBuilder();
		for (FieldError error : bindingResult.getFieldErrors()) {
			errorMsg.append(error.getField());
			errorMsg.append("：");
			errorMsg.append(error.getDefaultMessage());
			errorMsg.append("； ");
		}
		return errorMsg.toString();

	}

	/*
	 * 将错误信息放到map中(insert时，验证参数)
	 * 
	 * @param BindingResult 参数验证结果
	 * 
	 * @return 返回错误信息map
	 */
	public static Map<String, String> error2map(BindingResult bindingResult) {
		Map<String, String> map = new HashMap<String, String>();
		for (FieldError error : bindingResult.getFieldErrors()) {
			map.put(error.getField(), error.getDefaultMessage());
		}
		return map;

	}

	/*
	 * 将错误信息放到map中(updateUI时，验证参数id)
	 * 
	 * @param Object id
	 * 
	 * @return 返回错误信息map
	 */
	public static Map<String, String> error2map(Object id) {
		Map<String, String> map = new HashMap<String, String>();
		if (id == null) {
			map.put("id", "不能为空");
		}
		return map;
	}

	/*
	 * 将错误信息放到map中(update时，要同时验证参数id)
	 * 
	 * @param BindingResult 参数验证结果
	 * 
	 * @return 返回错误信息map
	 */
	public static Map<String, String> error2map(BindingResult bindingResult, Object id) {
		Map<String, String> map = error2map(bindingResult);
		if (id == null) {
			map.put("id", "不能为空");
		}
		return map;
	}

}
