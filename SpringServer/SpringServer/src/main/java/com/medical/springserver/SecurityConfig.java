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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig{


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	http
    		// ...
    	.authorizeHttpRequests((authorize) -> authorize
    	        .anyRequest().permitAll()
    	    )
    	.csrf(csrf -> csrf.disable())
    	.cors(cors -> cors.disable())
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
        return new BCryptPasswordEncoder();
    }


}