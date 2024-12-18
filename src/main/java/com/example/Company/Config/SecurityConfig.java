package com.example.Company.Config;

import com.example.Company.Security.JWTFilter;
import com.example.Company.Security.JwtTokenProvider;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF ni o'chirib qo'yish
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/login").permitAll() // Login URL-lariga ruxsat berish
                        .requestMatchers(HttpMethod.DELETE, "/api/employees/**").hasRole("DIRECTOR")
                        .requestMatchers("/api/employees/department-status").hasAnyRole("DIRECTOR", "DEPARTMENT_HEAD")
                        .requestMatchers("/api/employees/filter-by-age").hasAnyRole("DIRECTOR", "DEPARTMENT_HEAD")
                        .requestMatchers("/api/employees/total-salaries").hasAnyRole("DIRECTOR", "DEPARTMENT_HEAD")
                        .requestMatchers("/api/employees/list").hasAnyRole("DIRECTOR", "DEPARTMENT_HEAD")
                        .requestMatchers("/api/employees/**").hasAnyRole( "DIRECTOR","EMPLOYEE", "DEPARTMENT_HEAD")
                        .requestMatchers(HttpMethod.DELETE,"/api/clients/**").hasAnyRole("DIRECTOR","DEPARTMENT_HEAD")
                        .requestMatchers("/api/clients/**").hasAnyRole("DIRECTOR","EMPLOYEE", "DEPARTMENT_HEAD")
                        .requestMatchers(HttpMethod.DELETE,"/api/expenses/**").hasAnyRole("DIRECTOR","DEPARTMENT_HEAD")
                        .requestMatchers("/api/expenses/expenses-per-type").hasAnyRole("DIRECTOR","DEPARTMENT_HEAD")
                        .requestMatchers("/api/expenses/most-expensive-type").hasAnyRole("DIRECTOR","DEPARTMENT_HEAD")
                        .requestMatchers("/api/expenses/started-last-month").hasAnyRole("DIRECTOR","DEPARTMENT_HEAD")
                        .requestMatchers("/api/expenses/stopped-last-month").hasAnyRole("DIRECTOR","DEPARTMENT_HEAD")
                        .requestMatchers("/api/expenses/top-spender-employee").hasAnyRole("DIRECTOR","DEPARTMENT_HEAD")
                        .requestMatchers("/api/expenses/**").hasAnyRole("DIRECTOR","DEPARTMENT_HEAD", "EMPLOYEE")
                        .requestMatchers("/api/logs/**").hasAnyRole("DIRECTOR")
                        .anyRequest().authenticated() // Boshqa barcha so'rovlarni autentifikatsiyaga muhtoj qilish
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedHandler((request, response, accessDeniedException) -> {

                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            response.getWriter().write("Sizning lavozimingiz bu amalni bajarishga ruxsat bermaydi.");
                            response.getWriter().flush();
                        })
                )
                .addFilterBefore(new JWTFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class); // JWTFilter-ni qo'shish

        return http.build();
    }



    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
