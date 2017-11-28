package com.app.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@EnableAutoConfiguration
public class PanonoAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(PanonoAppApplication.class, args);
	}
}
