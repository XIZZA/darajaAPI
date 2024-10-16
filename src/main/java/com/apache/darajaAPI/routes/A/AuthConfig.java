package com.apache.darajaAPI.routes.A;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Setter
@AllArgsConstructor
@Configuration
@PropertySource("classpath:auth.json")
public class AuthConfig {

    @Value("${username}")
    private String username;

    @Value("${password}")
    private String password;

    // Getters and setters for username and password (optional)

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}