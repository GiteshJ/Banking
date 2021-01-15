package com.banking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.dto.UserCrendentials;
import com.banking.dto.UserDto;
import com.banking.service.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;
	
	
	@PostMapping("/addEmployee")
	public String saveEmployee(@RequestBody UserDto user) {
		return employeeService.addEmployee(user);
	}
	
	@DeleteMapping("/deleteEmployee/{username}")
	public String deleteEmployee(@PathVariable String username) {
		return employeeService.deleteEmployee(username);
	}
	
	@PostMapping("/authenticate")
	public String authenticate(@RequestBody UserCrendentials userCrendentials) {
		return "";
	}
}
