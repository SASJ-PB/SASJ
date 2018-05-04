package br.edu.ifpb.monteiro.ads.sasj.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import br.edu.ifpb.monteiro.ads.sasj.api.property.SasjApiProperty;

@SpringBootApplication
@EnableConfigurationProperties(SasjApiProperty.class)
public class SasjApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SasjApiApplication.class, args);
	}
	
}