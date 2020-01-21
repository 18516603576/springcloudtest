package com.springcloudtest.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {
	@GetMapping("info")
	public String info(String username) {
		return username + "1101";
	}
}
