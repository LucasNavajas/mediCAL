package com.medical.springserver;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import jakarta.annotation.PostConstruct;

@Configuration
public class FirebaseConfig {

	@Value("${firebase.config.path}")
	private String configPath;
	
	@PostConstruct
	public void init() throws IOException {
		InputStream serviceAccount = getClass().getResourceAsStream("/medicalauth-9419f-firebase-adminsdk-bhx2z-662ef38153.json");
		FirebaseOptions options = FirebaseOptions.builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
			    .build();

			FirebaseApp.initializeApp(options);
	}
}
