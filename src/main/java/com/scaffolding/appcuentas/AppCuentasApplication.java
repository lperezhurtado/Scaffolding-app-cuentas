package com.scaffolding.appcuentas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class AppCuentasApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppCuentasApplication.class, args);
	}

    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
