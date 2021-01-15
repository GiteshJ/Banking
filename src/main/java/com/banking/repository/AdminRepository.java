package com.banking.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.banking.model.Admin;

public interface AdminRepository extends MongoRepository<Admin, Long>{

	Admin findByUserName(String username);
	
}
