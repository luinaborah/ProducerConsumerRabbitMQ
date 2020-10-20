package com.rabbitmqspringboot.producerconsumer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rabbitmqspringboot.producerconsumer.component.Consumer;

@Configuration
public class RabbitMQFanoutConfig {

	@Bean
	Queue queueOne() {
		return new Queue("queueOne", false);
	}

	@Bean
	Queue queueTwo() {
		return new Queue("queueTwo", false);
	}

	@Bean
	Queue queueThree() {
		return new Queue("queueThree", false);
	}

	@Bean
	FanoutExchange exchange() {
		return new FanoutExchange("fanout-exchange");
	}

	@Bean
	Binding queueOneBinding(Queue queueOne, FanoutExchange exchange) {
		return BindingBuilder.bind(queueOne).to(exchange);
	}

	@Bean
	Binding queueTwoBinding(Queue queueTwo, FanoutExchange exchange) {
		return BindingBuilder.bind(queueTwo).to(exchange);
	}

	@Bean
	Binding queueThreeBinding(Queue queueThree, FanoutExchange exchange) {
		return BindingBuilder.bind(queueThree).to(exchange);
	}

	@Bean
	MessageListenerAdapter listenerAdapter(Consumer consumer) {
		return new MessageListenerAdapter(consumer, "receiveMessage");
	}

	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames("queueOne", "queueTwo");
		container.setMessageListener(listenerAdapter);
		return container;
	}

	@Bean
	RabbitTemplate amqpTemplate(ConnectionFactory factory) {
		RabbitTemplate template = new RabbitTemplate(factory);
		template.setRoutingKey("remoting.binding");
		template.setExchange("remoting.exchange");
		return template;
	}
}
