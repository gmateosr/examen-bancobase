package com.base.documents;

import org.springframework.data.mongodb.core.mapping.Document;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.mongojack.ObjectId;
import org.springframework.data.annotation.Id;

import lombok.Builder;
import lombok.Data;

@Document(collection = "transaction")
@Data
@Builder
public class Transaction {
	
	@ObjectId
	@Id
	private String id;	
	private String transactionType;
	private BigDecimal amount;
	private String currency;
	private String description;
	private String status;
	private String createdAt;
	private AccountDetails accountDetails;
	private List<Product> products;
	private String updateAt;
	private String lastStatus;
}
