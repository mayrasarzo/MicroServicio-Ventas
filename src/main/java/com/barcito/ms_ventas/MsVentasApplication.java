package com.barcito.ms_ventas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan({"com.barcito.controller", "com.barcito.service", "com.barcito.repository", "com.barcito.config"})
@EnableJpaRepositories("com.barcito.repository")
@EntityScan("com.barcito.entity")
@SpringBootApplication
public class MsVentasApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsVentasApplication.class, args);
	}

}
