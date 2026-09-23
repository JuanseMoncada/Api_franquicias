package com.franquicias.franquicias_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class FranquiciasApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FranquiciasApiApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void onAplicacion(){
		System.out.println("===============");
		System.out.println("Franquicias API");
		System.out.println("===============");
	}

}
