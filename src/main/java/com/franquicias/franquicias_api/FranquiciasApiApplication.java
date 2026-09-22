package com.franquicias.franquicias_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FranquiciasApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FranquiciasApiApplication.class, args);
	}

	public void onAplicacion(){
		System.out.println("===============");
		System.out.println("Franquicias API");
		System.out.println("===============");
	}


}
