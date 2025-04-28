package com.base.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Notificacion {
	
	private String createAt;
	private String nameNotification;
	private Object obj;

}