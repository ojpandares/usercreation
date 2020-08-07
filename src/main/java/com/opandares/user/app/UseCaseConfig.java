package com.opandares.user.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opandares.user.domain.gateway.AuthenticationGateway;
import com.opandares.user.domain.model.user.UserRepository;
import com.opandares.user.domain.usescase.user.UserUseCase;
import com.opandares.user.infrastructure.adapter.JwtAuthenticationAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public AuthenticationGateway authenticationGateway(@Value("${jwt.secret}") String secret){
        return new JwtAuthenticationAdapter(secret);
    }

    @Bean
    public UserUseCase userUseCase(UserRepository userRepository, ObjectMapper objectMapper, AuthenticationGateway authenticationGateway){

        return new UserUseCase(userRepository, objectMapper,authenticationGateway);
    }
}
