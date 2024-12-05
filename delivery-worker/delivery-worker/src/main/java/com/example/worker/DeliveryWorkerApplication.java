package com.example.worker;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class DeliveryWorkerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeliveryWorkerApplication.class, args);
	}


}
