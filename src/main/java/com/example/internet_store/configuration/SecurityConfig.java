package com.example.internet_store.configuration;

import com.example.internet_store.services.PersoneDetailService;
import org.apache.catalina.filters.ExpiresFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    final PersoneDetailService personeDetailService;
    @Autowired
    public SecurityConfig(PersoneDetailService personeDetailService) {
        this.personeDetailService = personeDetailService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
       // System.out.println("start!!!!!!!!!!!!!!!");
        http.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable()).authorizeRequests().requestMatchers("/auth/login", "/error", "/auth/registration").permitAll().
                anyRequest().authenticated().and().formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer.loginPage("/auth/login").
                        loginProcessingUrl("/process_login").defaultSuccessUrl("/main", true).
                        failureUrl("/auth/login?error"));

        return http.build();
    }

    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(personeDetailService);
    }
    @Bean
    protected PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

}
