package com.base.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.dto.Response;
import com.base.dto.TransactionRequest;
import com.base.dto.TransactionStatusRequest;
import com.base.services.TransactionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/transaction")
public class TransactionController {
	public static final Logger log = LoggerFactory.getLogger(TransactionController.class);
	@Autowired
	TransactionService transactionService;
	
	@GetMapping("")
	public Response getAllTransactions() {
		Map<String, Object> resp = new HashMap<>();
		
		try {
			resp.put("success", transactionService.getAllTransaction());
			return Response.builder().code("200").status("OK").msg(resp).build();			
		}catch (Exception e) {
			resp.put("error", e.getMessage());			
			return Response.builder().code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
					.status(HttpStatus.INTERNAL_SERVER_ERROR.name())
					.msg(resp).build();
		}				
	}
	
	@PostMapping("")
	public ResponseEntity<?> create(@Valid @RequestBody TransactionRequest transaction) {		
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.saveTransaction(transaction));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(String.format("{\"error\":\"%s\"}", e.getMessage()));
		}
	}	
	
	@GetMapping("/{id}")
	public Response getTransaction(@PathVariable String id) {
		Map<String, Object> resp = new HashMap<>();
		
		try {			
			resp.put("success", transactionService.getTransaction(id));
			return Response.builder().code(String.valueOf(HttpStatus.OK.value())).status(HttpStatus.OK.name())
					.msg(resp).build();
		} catch (Exception e) {
			resp.put("error", e.getMessage());			
			return Response.builder().code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
					.status(HttpStatus.INTERNAL_SERVER_ERROR.name())
					.msg(resp).build();

		}		
		
	}

	@GetMapping("/status/{id}")
	public Response getTransactionStatus(@PathVariable String id) {
		Map<String, Object> resp = new HashMap<>();
		
		try {			
			resp.put("success", transactionService.getTransactionStatus(id));
			return Response.builder().code(String.valueOf(HttpStatus.OK.value())).status(HttpStatus.OK.name())
					.msg(resp).build();
		} catch (Exception e) {
			resp.put("error", e.getMessage());			
			return Response.builder().code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
					.status(HttpStatus.INTERNAL_SERVER_ERROR.name())
					.msg(resp).build();

		}
	}
	
	@PutMapping("/status")
	public ResponseEntity<?>  updateTransactionStatus(@Valid @RequestBody TransactionStatusRequest transaction) {		
		try {			
			return ResponseEntity.status(HttpStatus.OK).body(transactionService.updateTransactionStatus(transaction));		
		} catch (Exception e) {	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(String.format("{\"error\":\"%s\"}", e.getMessage()));
		}
	}
	
	@PutMapping("/last/status")
	public ResponseEntity<?>  updateLastTransactionStatus(@Valid @RequestBody TransactionStatusRequest transaction) {		
		try {
			log.info("execute last/status");
			return ResponseEntity.status(HttpStatus.OK).body(transactionService.updateLastTransactionStatus(transaction));		
		} catch (Exception e) {	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(String.format("{\"error\":\"%s\"}", e.getMessage()));
		}
	}		



}
