package com.base.services;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.dto.Notificacion;
import com.base.dto.TransactionStatusRequest;
import com.base.dto.TransactionStatusResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Service
public class ConsumerServiceImpl implements ConsumerService{
	private final Logger log = LoggerFactory.getLogger(ConsumerServiceImpl.class);
	
	@Autowired
	private OkHttpClient client = new OkHttpClient();

	@Override
	public void updateDate(Notificacion notificacion) throws Exception {
		log.info("Notificacion recibida: {}", notificacion.getObj().toString());
		

		log.info("Ejecuta :updateLastStatus {}", notificacion.getObj().toString());
		String[] json = notificacion.getObj().toString().split(",");
		
		String id = json[0].split("=")[1];
		String status = json[1].split("=")[1];
		
		doPostRequest("http://localhost:8080/api/v1/transaction/last/status", TransactionStatusRequest.builder().id(id).status(status).build().toString());
	}
	
    String doPostRequest(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
          Request request = new Request.Builder()
              .url(url)
              .put(body)
              .build();
          Response response = client.newCall(request).execute();
          return response.body().string();
    }	

}
