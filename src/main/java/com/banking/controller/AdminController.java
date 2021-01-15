package com.banking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.dto.UserCrendentials;
import com.banking.dto.UserDto;
import com.banking.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	AdminService adminService;
	
	@PostMapping("/sec/bypass")
	public String bypass(@RequestBody UserDto user) {
		return "";
	}
	
	@PostMapping("/addAdmin")
	public String saveAdmin(@RequestBody UserDto user) {
		return adminService.addAdmin(user);
	}
	
	
	
	@PostMapping("/authenticate")
	public String authenticate(@RequestBody UserCrendentials userCrendentials) {
		return "";
	}
	
}
