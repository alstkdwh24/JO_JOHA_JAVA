package com.example.trip;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;

@SpringBootApplication
public class TripApplication {
	public static void main(String[] args) {
		SpringApplication.run(TripApplication.class, args);
	}
}
