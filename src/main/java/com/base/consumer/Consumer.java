package com.base.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.base.services.ConsumerServiceImpl;

import com.base.dto.Notificacion;

@Service
public class Consumer {
	private static final Logger log = LoggerFactory.getLogger(Consumer.class);
	
	@Autowired
	private ConsumerServiceImpl service;
	
	@RabbitListener(queues ={"${rabbitmq.queue.json.name}"})
	public void consumeMesagge(Notificacion notifiacion) {
		try {
			log.info("actualiza estatus");
			service.updateDate(notifiacion);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
