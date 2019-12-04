package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Administrator
 */
@SpringBootApplication
//@ComponentScan(basePackages = {"com.*"})
public class SpringBootMainApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringBootMainApplication.class, args);
	}

}
