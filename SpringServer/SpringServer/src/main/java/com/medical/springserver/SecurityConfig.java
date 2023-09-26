package com.medical.springserver;

import org.springframework.context.annotation.Bean;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfig{

	@Bean
	public WebMvcConfigurer corsConfigurer() {
	    return new WebMvcConfigurer() {
	        @Override
	        public void addCorsMappings(CorsRegistry registry) {
	            registry.addMapping("/**")
	                .allowedOrigins("*", "http://localhost:8081") // Permitir desde cualquier origen y localhost:8081
	                .allowedMethods("GET", "POST", "PUT", "DELETE") // Permitir los métodos que necesitas
	                .allowedHeaders("*"); // Permitir todos los encabezados
	        }
	    };
	}

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	http
    		// ...
    	.authorizeHttpRequests((authorize) -> authorize
    	        .anyRequest().permitAll()
    	    )
    	.csrf(csrf -> csrf.disable())
    		.httpBasic(withDefaults());
    	return http.build();
    }
    
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
    	UserDetails admin = User.builder()
    			.username("admin")
                .password("admin")
                .roles("USER")
                .build();
            return new InMemoryUserDetailsManager(admin);
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
    	return new MessageDigestPasswordEncoder("SHA-256");
    }
   



}