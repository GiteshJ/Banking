package com.banking.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banking.ValidationUtil.UserValidation;
import com.banking.dto.UserDto;
import com.banking.model.Employee;
import com.banking.repository.EmployeeRepository;

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
			Optional<Employee> emp = employeeRepository.findByUserName(username);
			emp.orElseThrow(() -> new Exception("Invalid details!"));
			
			employeeRepository.deleteById(Long.valueOf(emp.get().getId()));
		}
		catch(Exception e) {
			return "Employee does not exist";
		}
		return "Employee Successfully deleted";
		
	}
	
	public String addEmployee(UserDto user) {
		
		try {
			if(!UserValidation.validate(user)) throw new Exception("Invalid details!");
			
			Optional<Employee> empOp = employeeRepository.findByUserName(user.getUserName());
			if(empOp.isPresent()) throw new Exception("Invalid details!");
			
			
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
			e.printStackTrace();
			return "Employee Cannot be created try another username";
		}
		return "Employee Successfully Registered";
		
	}
	
	
	
	
}
