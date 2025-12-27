package com.medilabo.RiskService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.medilabo.RiskService")
public class RiskServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RiskServiceApplication.class, args);
	}

}
