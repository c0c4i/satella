package it.univr.satella;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class SatellaApplication {
	public static void main(String[] args) {
		SpringApplication.run(SatellaApplication.class, args);
	}
}
