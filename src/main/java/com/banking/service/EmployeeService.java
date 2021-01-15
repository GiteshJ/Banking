package com.banking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banking.dto.UserDto;
import com.banking.model.Employee;
import com.banking.repository.EmployeeRepository;
import com.banking.util.UserValidation;

@Transactional
@Service
public class EmployeeService {
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	SequenceGeneratorService sequenceGenerator;
	
	public String deleteEmployee(String username) {
		
		try {
			Employee emp = employeeRepository.findByUserName(username);
			if(emp==null) throw new Exception("Invalid details!");
			employeeRepository.deleteById(Long.valueOf(emp.getId()));
		}
		catch(Exception e) {
			return "Employee does not exist";
		}
		return "Employee Successfully deleted";
		
	}
	
	public String addEmployee(UserDto user) {
		
		try {
			if(!UserValidation.validate(user)) throw new Exception("Invalid details!");
			if(employeeRepository.findByUserName(user.getUserName())!=null) throw new Exception("Invalid details!");
			Employee emp = new Employee();
			emp.setUserName(user.getUserName());
			emp.setPassword(passwordEncoder.encode(user.getPassword()));
			emp.setFirstName(user.getFirstName());
			emp.setLastName(user.getLastName());
			emp.setRoles("ROLE_EMPLOYEE");
			emp.setId(sequenceGenerator.generateSequence(Employee.SEQUENCE_NAME));
			employeeRepository.save(emp);
		}
		catch(Exception e) {
			return "Employee Cannot be created try another username";
		}
		return "Employee Successfully Registered";
		
	}
	
	
	
	
}
