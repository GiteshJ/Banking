package com.banking.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.banking.model.Customer;

public interface CustomerRepository extends MongoRepository<Customer,Long>{

}
