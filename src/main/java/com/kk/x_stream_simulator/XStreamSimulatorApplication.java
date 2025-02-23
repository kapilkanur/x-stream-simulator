package com.kk.x_stream_simulator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class XStreamSimulatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(XStreamSimulatorApplication.class, args);
	}

}
