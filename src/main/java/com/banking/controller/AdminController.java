package com.banking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.dto.UserCrendentials;
import com.banking.model.Admin;
import com.banking.model.Employee;
import com.banking.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	AdminService adminService;
	
	@PostMapping("/addAdmin")
	public String save(@RequestBody Admin admin) {
		return adminService.addAdmin(admin);
	}
	
	@PostMapping("/addEmployee")
	public String save(@RequestBody Employee employee) {
		return adminService.addEmployee(employee);
	}
	
	@DeleteMapping("/deleteEmployee/{username}")
	public String save(@PathVariable String username) {
		return adminService.deleteEmployee(username);
	}
	
	@PostMapping("/authenticate")
	public String save(@RequestBody UserCrendentials userCrendentials) {
		return "";
	}
	
}
