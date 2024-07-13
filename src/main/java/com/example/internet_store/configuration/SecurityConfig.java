package com.example.internet_store.configuration;

import com.example.internet_store.services.PersoneDetailService;
import org.apache.catalina.filters.ExpiresFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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



//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .requestMatchers("/product","/auth/login", "/product/view/{productURL}",
//                        "/download/**", "/cart/**", "/order/createOrder", "/main", "auth/registration", "/myorders").permitAll().requestMatchers( "/user/profile").hasAnyRole("USER", "ADMIN", "SUPERADMIN").
//            anyRequest().hasAnyRole("ADMIN","SUPERADMIN").
//            and().formLogin(httpSecurityFormLoginConfigurer -> {
//                        httpSecurityFormLoginConfigurer.loginPage("/auth/login").
//                                loginProcessingUrl("/process_login").defaultSuccessUrl("/main", true).
//                                failureUrl("/auth/login?error");}).logout
//                        (httpSecurityLogoutConfigurer -> {httpSecurityLogoutConfigurer.logoutUrl("/logout").
//                        logoutSuccessUrl("/auth/login");});
//        return http.build();
//    }
//


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .requestMatchers("/admin").hasRole("ADMIN").requestMatchers( "/user/profile").hasAnyRole("USER", "ADMIN").anyRequest().permitAll().
                and().formLogin(httpSecurityFormLoginConfigurer -> {
                    httpSecurityFormLoginConfigurer.loginPage("/auth/login").
                            loginProcessingUrl("/process_login").defaultSuccessUrl("/main", true).
                            failureUrl("/auth/login?error");}).logout
                        (httpSecurityLogoutConfigurer -> {httpSecurityLogoutConfigurer.logoutUrl("/logout").
                                logoutSuccessUrl("/auth/login");});
        return http.build();
    }



    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(personeDetailService).passwordEncoder(getPasswordEncoder());

    }
    @Bean
    protected PasswordEncoder getPasswordEncoder() {
   //     return NoOpPasswordEncoder.getInstance();
        return  new BCryptPasswordEncoder();
    }

}
