package com.opandares.user.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opandares.user.domain.gateway.AuthenticationGateway;
import com.opandares.user.domain.gateway.UserGateway;
import com.opandares.user.domain.usescase.login.LoginUseCase;
import com.opandares.user.domain.usescase.user.UserUseCase;
import com.opandares.user.infrastructure.adapter.JwtAuthenticationAdapter;
import com.opandares.user.infrastructure.adapter.UserMapperAdapter;
import com.opandares.user.infrastructure.adapter.UserServiceAdapter;
import com.opandares.user.infrastructure.data.UserRepository;
import com.opandares.user.infrastructure.mapper.UserMapper;
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
    public UserUseCase userUseCase(UserGateway userGateway){

        return new UserUseCase(userGateway);
    }

    @Bean
    public UserGateway userGateway(UserRepository userRepository, AuthenticationGateway authenticationGateway,

                                   UserMapper userMapper, ObjectMapper objectMapper){

        return new UserServiceAdapter(userRepository, userMapper,authenticationGateway, objectMapper);
    }

    @Bean
    public LoginUseCase loginUseCase(UserGateway userGateway){

        return new LoginUseCase(userGateway);
    }

    @Bean
    public UserMapper userMapper(ObjectMapper objectMapper){
        return new UserMapperAdapter(objectMapper);
    }
}
