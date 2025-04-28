package com.base.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.base.documents.Transaction;

@Repository
public interface TransactionRepository extends MongoRepository <Transaction, String>{

}
