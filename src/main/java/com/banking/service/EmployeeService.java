package com.banking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.banking.dto.UserDto;
import com.banking.model.Customer;
import com.banking.model.Employee;
import com.banking.repository.CustomerRepository;
import com.banking.repository.EmployeeRepository;
import com.banking.util.UserValidation;

@Service
public class EmployeeService {
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public String deleteCustomer(String username) {
		
		try {
			Customer cust = customerRepository.findByUserName(username);
			if(cust==null) throw new Exception("Invalid details!");
			customerRepository.deleteById(Long.valueOf(cust.getId()));
		}
		catch(Exception e) {
			return "Customer Cannot be created try another username";
		}
		return "Customer Successfully Registered";
		
	}
	
	public String addCustomer(UserDto user) {
		
		try {
			if(!UserValidation.validate(user)) throw new Exception("Invalid details!");
			Customer cust = new Customer();
			cust.setUserName(user.getUserName());
			cust.setPassword(passwordEncoder.encode(user.getPassword()));
			cust.setFirstName(user.getFirstName());
			cust.setLastName(user.getLastName());
			customerRepository.save(cust);
		}
		catch(Exception e) {
			return "Customer Cannot be created try another username";
		}
		return "Customer Successfully Registered";
		
	}
	
	
}
