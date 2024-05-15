package com.example.springmembermvc.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfig {
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(authorizeRequests ->
//                        authorizeRequests
//                                .requestMatchers("/loginn", "/css/**", "/js/**").permitAll() // Allow access to the login page and static resources
//                                .anyRequest().authenticated()
//                )
//                .formLogin(formLogin ->
//                        formLogin
//                                .loginPage("/loginn")
//                                .defaultSuccessUrl("/index", true)
//                                .permitAll()
//                )
//                .logout(logout ->
//                        logout
//                                .permitAll()
//                );
//
//        return http.build();
//    }
}
