package com.base.documents;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Account {

	private long accountNumber;
	private String name;
	private String bank;
	private String typeAccount;
}
