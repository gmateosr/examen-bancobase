package com.base.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class RabbitMQConfig {
	
	@Value("${rabbitmq.queue.name}")
	private String queue;
	
	@Value("${rabbitmq.queue.json.name}")
	private String jsonQueue;
	
	@Value("${rabbitmq.exchange.name}")
	private String exchange;
	
	@Value("${rabbitmq.routing.key}")
	private String routingKey;
	
	@Value("${rabbitmq.routing.json.key}")
	private String routingJsonKey;

	
	@Bean
	public Queue jsonQueue() {
		return new Queue(jsonQueue);
	}
	
	@Bean
	public TopicExchange exchange() {
		return new TopicExchange(exchange);
	}
	
	@Bean
	public Binding jsonBinding() {
		return BindingBuilder.bind(jsonQueue())
				.to(exchange())
				.with(routingJsonKey);
	}	
	
	@Bean
	public MessageConverter jsonMassageConverter() {
		return new Jackson2JsonMessageConverter();
		
	}
	
	@Bean
	public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {		
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		
		rabbitTemplate.setMessageConverter(jsonMassageConverter());
		return rabbitTemplate;		
	}
	
	

}
