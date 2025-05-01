package com.base.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.documents.Transaction;
import com.base.dto.Notificacion;
import com.base.dto.TransactionRequest;
import com.base.dto.TransactionStatusRequest;
import com.base.dto.TransactionStatusResponse;
import com.base.producer.Producer;
import com.base.repository.TransactionRepository;
import com.base.utils.ExceptionTransaction;

@Service
public class TransactionServiceImpl implements TransactionService {
	
	private static final Logger log = LogManager.getLogger(TransactionServiceImpl.class);
	private static final String NOTIFICACION_CHANGE_STATUS = "NOTIFICACION_CHANGE_STATUS";
	private static final String TRANSACTION_STATUS = ",CREATED,VALIDATION,PROGRESS,FINALLY,CANCELLED,";
	
	@Autowired
	TransactionRepository repo;
	
	@Autowired
	Producer producer;

	@Override
	public TransactionRequest saveTransaction(TransactionRequest transaction) throws Exception{
		log.info("Start -> saveTransaction");
		
		if (Objects.isNull(transaction))
			throw new ExceptionTransaction("Request is null");			
		
		com.base.documents.Transaction transactionSave = repo.save(com.base.documents.Transaction.builder()
				.accountDetails(transaction.getAccountDetails()).amount(transaction.getAmount())
				.createdAt(transaction.getCreatedAt()).currency(transaction.getCurrency())
				.description(transaction.getDescription()).products(transaction.getProducts())
				.status(transaction.getStatus()).transactionType(transaction.getTransactionType()).build());

		transaction.setId(transactionSave.getId());
		log.info("save process success -> saveTransaction");
		
		return transaction;	
	}

	@Override
	public List<TransactionRequest> getAllTransaction() throws Exception{
		List<TransactionRequest> transactions = new ArrayList<>();
		List<Transaction> listTransactions = repo.findAll();
		
		listTransactions.forEach((x) -> {
			
			transactions.add(
			TransactionRequest.builder()
			.accountDetails(x.getAccountDetails()).amount(x.getAmount())
			.createdAt(x.getCreatedAt()).currency(x.getCurrency())
			.description(x.getDescription()).products(x.getProducts())
			.status(x.getStatus()).transactionType(x.getTransactionType())
			.id(x.getId()).build()
			);
			
		});
		return transactions;
	}
	
	@Override
	public TransactionStatusResponse getTransactionStatus(String id) throws Exception {
		log.info("Start -> getTransactionStatus");
		Transaction transactionFound = validateUpdate(TransactionRequest.builder().id(id).build());
		log.info("ValidateUpdate process success :: getTransactionStatus");
		return TransactionStatusResponse.builder().id(transactionFound.getId()).status(transactionFound.getStatus()).build();
	}
		

	@Override
	public TransactionRequest updateTransaction(TransactionRequest transaction) throws Exception {
		log.info("Start -> updateTransaction");
		
		if (Objects.isNull(transaction))
			throw new ExceptionTransaction("Request is null");		
		
		Transaction transactionFound = validateUpdate(transaction);
		log.info("ValidateUpdate process success -> updateTransaction");
		transactionFound.setUpdateAt(LocalDateTime.now().toString());
		repo.save(transactionFound);
		
		TransactionRequest transactionRequest = TransactionRequest.builder()
				.accountDetails(transaction.getAccountDetails()).amount(transaction.getAmount())
				.createdAt(transaction.getCreatedAt()).currency(transaction.getCurrency())
				.description(transaction.getDescription()).products(transaction.getProducts())
				.status(transaction.getStatus()).transactionType(transaction.getTransactionType())
				.id(transactionFound.getId()).updateAt(transaction.getUpdateAt()).build();
		
		return transactionRequest;
	}

	@Override
	public TransactionStatusResponse updateTransactionStatus(TransactionStatusRequest transaction) throws Exception {
		log.info("Start -> updateTransactionStatus");
		
		if (Objects.isNull(transaction))
			throw new ExceptionTransaction("Request is null");
		
		Transaction transactionFound = validateUpdate( TransactionRequest.builder().id(transaction.getId()).build());
		transactionFound.setStatus(validateUpdateStatus(transaction.getStatus()) ? transaction.getStatus().toUpperCase() : null);
		transactionFound.setUpdateAt(LocalDateTime.now().toString());
		repo.save(transactionFound);
		log.info("update process success :: updateTransactionStatus");
		
		TransactionStatusResponse transactionStatusResponse = TransactionStatusResponse.builder()
				.status(transactionFound.getStatus())
				.id(transactionFound.getId())
				.updateAt(transactionFound.getUpdateAt()).build();
		producer.sendJsonMesagge(Notificacion.builder().createAt(LocalDateTime.now().toString()).nameNotification(NOTIFICACION_CHANGE_STATUS).obj(transactionStatusResponse).build());
		
		log.info("send notification -> updateTransactionStatus");
		
		return transactionStatusResponse;
	}
	
	@Override
	public TransactionRequest getTransaction(String id) throws Exception {
		log.info("Start -> getTransaction");
		Transaction transaction = validateUpdate(TransactionRequest.builder().id(id).build());
		return TransactionRequest.builder()
				.accountDetails(transaction.getAccountDetails()).amount(transaction.getAmount())
				.createdAt(transaction.getCreatedAt()).currency(transaction.getCurrency())
				.description(transaction.getDescription()).products(transaction.getProducts())
				.status(transaction.getStatus()).transactionType(transaction.getTransactionType())
				.id(transaction.getId()).updateAt(transaction.getUpdateAt()).lastStatus(transaction.getLastStatus()).build(); 
	}
	
	@Override
	public TransactionStatusResponse updateLastTransactionStatus(TransactionStatusRequest transaction)
			throws Exception {
		log.info("Start -> updateLastTransactionStatus");
		
		if (Objects.isNull(transaction))
			throw new ExceptionTransaction("Request is null");
		
		Transaction transactionFound = validateUpdate( TransactionRequest.builder().id(transaction.getId()).build());
		transactionFound.setLastStatus(validateUpdateStatus(transaction.getStatus()) ? transaction.getStatus().toUpperCase() : null);
		transactionFound.setUpdateAt(LocalDateTime.now().toString());
		repo.save(transactionFound);
		log.info("update process success :: updateLastTransactionStatus");
		
		return TransactionStatusResponse.builder()
				.lastStatus(transactionFound.getLastStatus())
				.id(transactionFound.getId())
				.updateAt(transactionFound.getUpdateAt()).build();
	}
	
	
	private boolean validateUpdateStatus(String statusp) throws ExceptionTransaction {		
		return TRANSACTION_STATUS.contains(",".concat(statusp.toUpperCase()).concat(","));
	}
	
	private Transaction validateUpdate(TransactionRequest transaction) throws ExceptionTransaction {
		Optional.ofNullable(transaction.getId()).orElseThrow(() -> new ExceptionTransaction("Id not found."));

		return repo.findById(transaction.getId())
				.orElseThrow(() -> new ExceptionTransaction("Transaction not found."));
	}


}
