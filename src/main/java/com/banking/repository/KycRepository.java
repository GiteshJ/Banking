package com.banking.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.banking.model.Kyc;

public interface KycRepository extends MongoRepository<Kyc,Long>{

}
