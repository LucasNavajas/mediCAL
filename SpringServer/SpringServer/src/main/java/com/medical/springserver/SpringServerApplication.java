package com.medical.springserver;
import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
@ComponentScan("com.medical")

@SpringBootApplication(scanBasePackages = "com.medical.springserver")
@EnableAutoConfiguration
@ComponentScan("com.medical.springserver")
@EnableScheduling

public class SpringServerApplication {

	public static void main(String[] args) throws IOException {
		
		SpringApplication.run(SpringServerApplication.class, args);
		
	}


}


