package com.base.documents;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Product {
	private String name;
	private BigDecimal amount;
	private String currency;
	private String productType;

}
