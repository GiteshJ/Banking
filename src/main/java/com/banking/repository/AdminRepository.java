package com.banking.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.banking.model.Admin;

public interface AdminRepository extends MongoRepository<Admin, Long>{

	Optional<Admin> findByUserName(String username);
	
}
