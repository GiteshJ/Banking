package com.banking.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.banking.model.Employee;

public interface EmployeeRepository extends MongoRepository<Employee,Long>{
	Optional<Employee> findByUserName(String username);
}
