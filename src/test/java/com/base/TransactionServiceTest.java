package com.base;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.base.documents.Account;
import com.base.documents.AccountDetails;
import com.base.documents.Product;
import com.base.documents.Transaction;
import com.base.dto.TransactionRequest;
import com.base.dto.TransactionStatusRequest;
import com.base.dto.TransactionStatusResponse;
import com.base.producer.Producer;
import com.base.repository.TransactionRepository;
import com.base.services.TransactionServiceImpl;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class TransactionServiceTest {
	
	@InjectMocks
	TransactionServiceImpl service;
	
	@Mock
	TransactionRepository repo;
	
	@Mock
	Producer producer;	
	
	@Mock
	Transaction inTransaction;
	
	@Mock
	Transaction ouTransaction;
	
	@BeforeEach
	void createContents() {
		MockitoAnnotations.openMocks(this);
		
		AccountDetails accountDetails = AccountDetails.builder()
				.beneficiary(Account.builder().accountNumber(12345678).bank("base").name("Gabriel")
						.typeAccount("Credit Card").build())
				.originator(Account.builder().accountNumber(123456789).bank("base").name("Gabriel")
						.typeAccount("Credit Debit").build())
				.build();
		
		ouTransaction = Transaction.builder().accountDetails(accountDetails).amount(BigDecimal.valueOf(7000.00))
				.createdAt(LocalDateTime.now().toString()).currency("MNX").description("Payment Game")
				.products(List.of(Product.builder().amount(BigDecimal.valueOf(7000)).currency("MNX")
						.name("Video Game").productType("Entreteiment").build()))
				.status("CREATED").transactionType("PAYMENT").id("680e3e47540e6a25c3afcaa9").build();		
	}
	
	@Test
	void testSaveTransaction() throws Exception {
		when(repo.save(any(Transaction.class))).thenReturn(ouTransaction);
		
		TransactionRequest ouTransactionSave = service.saveTransaction(TransactionRequest.builder().build());
		assertTrue(!ouTransactionSave.getId().isEmpty());
		assertEquals(ouTransaction.getId(), ouTransactionSave.getId());
	}
	
	@Test
	void testUpdateTransactionStatus() throws Exception {
		when(repo.findById(any(String.class))).thenReturn(Optional.of(ouTransaction));
		TransactionStatusResponse estatus = service.updateTransactionStatus(TransactionStatusRequest.builder().id("680e3e47540e6a25c3afcaa9").status("FINALLY").build());
		
		assertEquals("FINALLY", estatus.getStatus());
	}
	
	@Test
	void testUpdateLastTransactionStatus() throws Exception {
		when(repo.findById(any(String.class))).thenReturn(Optional.of(ouTransaction));
		TransactionStatusResponse estatus = service.updateLastTransactionStatus(TransactionStatusRequest.builder().id("680e3e47540e6a25c3afcaa9").status("CREATED").build());
		
		assertEquals("CREATED", estatus.getLastStatus());
	}

}
