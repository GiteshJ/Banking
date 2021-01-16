package com.banking.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banking.ValidationUtil.UserValidation;
import com.banking.common.CommonConstants;
import com.banking.customException.DuplicateUserNameException;
import com.banking.customException.InvalidDataException;
import com.banking.customException.UserNotFoundException;
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
	
	public void deleteEmployee(String username) throws Exception {
		
		if(username==null || username.equalsIgnoreCase("")) throw new InvalidDataException(CommonConstants.INVALID_DATA_ERROR_MESSAGE);
		Optional<Employee> emp = employeeRepository.findByUserName(username);
		emp.orElseThrow(() -> new UserNotFoundException(CommonConstants.EMPLOYEE_DOES_NOT_EXIST_ERROR_MESSAGE));
		
		employeeRepository.deleteById(Long.valueOf(emp.get().getId()));

		
		return;
		
	}
	
	public void addEmployee(UserDto user) throws Exception {
		
		
		if(!UserValidation.validate(user)) throw new InvalidDataException(CommonConstants.INVALID_DATA_ERROR_MESSAGE);
		
		Optional<Employee> empOp = employeeRepository.findByUserName(user.getUserName());
		if(empOp.isPresent()) throw new DuplicateUserNameException(CommonConstants.DUPLICATE_USERNAME_ERROR_MESSAGE);
		
		
		Employee emp = new Employee();
		emp.setUserName(user.getUserName());
		emp.setPassword(passwordEncoder.encode(user.getPassword()));
		emp.setFirstName(user.getFirstName());
		emp.setLastName(user.getLastName());
		emp.setRoles("ROLE_EMPLOYEE");
		emp.setId(sequenceGenerator.generateSequence(Employee.SEQUENCE_NAME));
		employeeRepository.save(emp);
		
		return;
		
	}
	
	
	
	
}
