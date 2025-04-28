package com.base.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.base.documents.AccountDetails;
import com.base.documents.Product;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionRequest {
	
	private String id;	
	@NotNull
	private String transactionType;
	@NotNull
	private BigDecimal amount;
	@NotNull
	private String currency;
	@NotNull
	private String description;
	@NotNull
	private String status;
	@NotNull
	private String createdAt;
	@NotNull
	private AccountDetails accountDetails;
	@NotNull
	private List<Product> products;
	private String updateAt;
	private String lastStatus;
	
}
