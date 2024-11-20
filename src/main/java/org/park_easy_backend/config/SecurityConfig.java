package org.park_easy_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/","/member/login","/member/save").permitAll()
                        .requestMatchers("/main").authenticated()
//                        .requestMatchers("/admin").hasRole("ADMIN")
//                        .requestMatchers("/main").hasAnyRole("ADMIN", "USER")
                        .anyRequest().authenticated()
                );

        http
                .formLogin((auth)->auth
                        .loginPage("/member/login")
                        .loginProcessingUrl("/main")
                        .defaultSuccessUrl("/main", true)
                        .permitAll()
                );

        http
                .logout((auth) -> auth
                        .logoutSuccessUrl("/member/login?logut")
                        .permitAll()
                );

        http
                .csrf((auth)->auth.disable());

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){

        return new BCryptPasswordEncoder();
    }
}


/**
 * loginPage() – the custom login page
 * loginProcessingUrl() – the URL to submit the username and password to
 * defaultSuccessUrl() – the landing page after a successful login
 * failureUrl() – the landing page after an unsuccessful login
 * logoutUrl() – the custom logout
 *
 */
