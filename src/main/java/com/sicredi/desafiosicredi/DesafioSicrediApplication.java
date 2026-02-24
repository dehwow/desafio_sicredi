package com.sicredi.desafiosicredi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DesafioSicrediApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesafioSicrediApplication.class, args);
	}

}
