package com.mehmetalierdogan.RepsySoftwarePackageSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
		"com.mehmetalierdogan",         // kendi app kodların
		"com.mehmeterdogan.storage"     // dış jar'dan gelen bean'ler
})
public class RepsySoftwarePackageSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(RepsySoftwarePackageSystemApplication.class, args);
	}

}
