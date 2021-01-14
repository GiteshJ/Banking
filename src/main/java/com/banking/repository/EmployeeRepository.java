package com.banking.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.banking.model.Employee;

public interface EmployeeRepository extends MongoRepository<Employee,Long>{
	Employee findByUserName(String username);
}
