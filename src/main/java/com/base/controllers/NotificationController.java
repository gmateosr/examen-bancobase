package com.base.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.dto.TransactionStatusRequest;
import com.base.producer.Producer;

@RestController
@RequestMapping("/api/v1/transaction")
public class NotificationController {

	@Autowired
	private Producer producer;
	
	@PostMapping("/notification/status")
	public ResponseEntity<String> sendMessageTransStatus(@RequestBody TransactionStatusRequest transaction){
		producer.sendJsonMesagge(transaction);
		return ResponseEntity.ok("Send Notification : OK");				
	}
	
}
