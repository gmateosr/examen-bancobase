package com.base.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionStatusRequest {
	
	@NotNull
	private String id;
	@NotNull
	private String status;

}
