package com.banking.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.banking.model.CustomerAccount;

public interface CustomerAccountRepository extends MongoRepository<CustomerAccount,Long> {

}
