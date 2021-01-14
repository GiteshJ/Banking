package com.banking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banking.dto.UserDto;
import com.banking.model.Admin;
import com.banking.model.Employee;
import com.banking.repository.AdminRepository;
import com.banking.repository.EmployeeRepository;
import com.banking.util.UserValidation;

@Service
@Transactional
public class AdminService {
	@Autowired
	AdminRepository adminRepository;
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	
	
	public String addAdmin(UserDto user) {
		
		try {
			if(!UserValidation.validate(user)) throw new Exception("Invalid details!");
			Admin admin = new Admin();
			admin.setUserName(user.getUserName());
			admin.setPassword(passwordEncoder.encode(user.getPassword()));
			admin.setFirstName(user.getFirstName());
			admin.setLastName(user.getLastName());
			admin.setRoles("ROLE_ADMIN");
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
			if(emp==null) throw new Exception("Invalid details!");
			employeeRepository.deleteById(Long.valueOf(emp.getId()));
		}
		catch(Exception e) {
			return "Employee does exist";
		}
		return "Employee Successfully deleted";
		
	}
	
	public String addEmployee(UserDto user) {
		
		try {
			if(!UserValidation.validate(user)) throw new Exception("Invalid details!");
			Employee emp = new Employee();
			emp.setUserName(user.getUserName());
			emp.setPassword(passwordEncoder.encode(user.getPassword()));
			emp.setFirstName(user.getFirstName());
			emp.setLastName(user.getLastName());
			emp.setRoles("ROLE_EMPLOYEE");
			employeeRepository.save(emp);
		}
		catch(Exception e) {
			return "Employee Cannot be created try another username";
		}
		return "Employee Successfully Registered";
		
	}
	
	
}
