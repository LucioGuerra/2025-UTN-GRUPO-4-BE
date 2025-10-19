package org.agiles.bolsaestudiantil;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication()
@EnableFeignClients(basePackages = "org.agiles.bolsaestudiantil.client")
public class BolsaestudiantilApplication {

	public static void main(String[] args) {
		SpringApplication.run(BolsaestudiantilApplication.class, args);
	}

}
