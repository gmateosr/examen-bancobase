package com.base.services;

import java.util.List;

import com.base.dto.TransactionRequest;
import com.base.dto.TransactionStatusRequest;
import com.base.dto.TransactionStatusResponse;

public interface TransactionService {
	
	TransactionRequest saveTransaction(TransactionRequest transaction) throws Exception;
	TransactionRequest updateTransaction(TransactionRequest transaction) throws Exception;
	TransactionRequest getTransaction(String id) throws Exception;
	List<TransactionRequest> getAllTransaction() throws Exception;
	
	TransactionStatusResponse updateTransactionStatus(TransactionStatusRequest transaction) throws Exception;
	TransactionStatusResponse getTransactionStatus(String id) throws Exception;
	TransactionStatusResponse updateLastTransactionStatus(TransactionStatusRequest transaction) throws Exception;

}
