package com.banking.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.banking.model.AccountType;

public interface AccountTypeRepository extends MongoRepository<AccountType,Long>{

}
