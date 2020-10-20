package com.rabbitmqspringboot.producerconsumer.component;

import java.util.concurrent.CountDownLatch;

import org.springframework.stereotype.Component;

@Component
public class Consumer {

	 private CountDownLatch latch = new CountDownLatch(1);

	  public void receiveMessage(String message) {
	    System.out.println("Consumer Received <" + message + ">");
	    latch.countDown();
	  }

	  public CountDownLatch getLatch() {
	    return latch;
	  }
}
