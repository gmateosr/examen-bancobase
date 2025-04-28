package com.base.dto;


import java.util.Map;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response {
	
	private String status;
	private String code;
	private Map<String,Object> msg;

}
