package com.yeonjin.security2.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.swing.plaf.PanelUI;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public WebSecurityCustomizer securityCustomizer(){
        return (web) -> web
                .ignoring()
                .requestMatchers(PathRequest
                        .toStaticResources()
                        .atCommonLocations());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests((authorizationManagerRequestMatcherRegistry -> {
            authorizationManagerRequestMatcherRegistry
                    .requestMatchers("/","/index.html").permitAll()
                    .requestMatchers("/member/register").anonymous()
                    .requestMatchers("/post/**").authenticated()
                    .requestMatchers("/admin/**").hasRole("ADMIN")
                    .anyRequest().authenticated();
        }));

        //formLogin설정
        http.formLogin((formLoginConfigurer -> {
            formLoginConfigurer
                    .loginPage("/auth/login")
                    .loginProcessingUrl("/auth/login")
                    .usernameParameter("memberId")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/")
                    .permitAll();
        }));

        http.logout(logoutConfigurer ->{
            logoutConfigurer
                    .logoutUrl("/auth/logout")
                    .logoutSuccessUrl("/");
        });

        return http.build();
    }
    
    //비밀번호 암호화
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
