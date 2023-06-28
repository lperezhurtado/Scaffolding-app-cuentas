package com.scaffolding.appcuentas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.scaffolding.appcuentas.Helper.IbanHelper;

@SpringBootApplication
public class AppCuentasApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppCuentasApplication.class, args);

		String iban = "ES2704875743916831544484";
		System.out.println("Validation Iban created: "+IbanHelper.validateIBAN2(iban));

		String validIban = IbanHelper.getValidIbanNumber();
		System.out.println("valid IBAN: "+validIban);
	}

    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
