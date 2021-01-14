package com.banking.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.banking.model.Admin;
import com.banking.model.Employee;
import com.banking.repository.AdminRepository;
import com.banking.repository.EmployeeRepository;

public class AdminService {
	@Autowired
	AdminRepository adminRepository;
	@Autowired
	EmployeeRepository employeeRepository;
	
	public String addAdmin(Admin admin) {
		
		try {
			adminRepository.save(admin);
		}
		catch(Exception e) {
			return "Admin Cannot be created try another username";
		}
		return "Admin Successfully Registered";
		
	}
	
	public String deleteEmployee(String username) {
		
		try {
			Employee emp = employeeRepository.findByUserName(username);
			employeeRepository.deleteById(Long.valueOf(emp.getId()));
		}
		catch(Exception e) {
			return "Admin Cannot be created try another username";
		}
		return "Admin Successfully Registered";
		
	}
	
	public String addEmployee(Employee employee) {
		
		try {
			employeeRepository.save(employee);
		}
		catch(Exception e) {
			return "employee Cannot be created try another username";
		}
		return "employee Successfully Registered";
		
	}
}
