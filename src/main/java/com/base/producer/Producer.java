package com.base.producer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Producer {
	private static final Logger log = LoggerFactory.getLogger(Producer.class);
	
	@Value("${rabbitmq.exchange.name}")
	private String exchange;
	
	@Value("${rabbitmq.routing.key}")
	private String routingKey;
	
	@Value("${rabbitmq.routing.json.key}")
	private String routingJsonKey;
	
	private RabbitTemplate rabbitTemplate;
	
	
	public Producer(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
		
	}
	
	public void sendJsonMesagge(Object mesagge) {
		log.info(String.format("Mesagge sent -> %s", mesagge.toString()));
		rabbitTemplate.convertAndSend(exchange,routingJsonKey,mesagge);
	}

}
