package com.banking.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.banking.model.CustomerAccount;

public interface CustomerAccountRepository extends MongoRepository<CustomerAccount,Long> {
	List<CustomerAccount> findByCustId(Long id);
}
