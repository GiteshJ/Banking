package com.banking.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.banking.model.Account;


public interface AccountRepository extends MongoRepository<Account,Long>{

	Account findByAccNum(Integer accNum);
}
