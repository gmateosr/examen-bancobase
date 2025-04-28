package com.base.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionStatusResponse {
	
	private String id;
	private String status;
	private String lastStatus;
	private String updateAt;
}
