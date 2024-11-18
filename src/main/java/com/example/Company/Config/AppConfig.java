package com.example.Company.Config;

import com.example.Company.Security.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class AppConfig {
    @Bean
    public JwtTokenProvider jwtTokenProvider(UserDetailsService userDetailsService) {
        return new JwtTokenProvider(userDetailsService);
    }
}

