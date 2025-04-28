package com.base.documents;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountDetails {

	private Account originator;
	private Account beneficiary;
}
