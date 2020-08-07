package com.opandares.user.domain.usescase.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opandares.user.domain.exception.InvalidEmailException;
import com.opandares.user.domain.exception.UserExistException;
import com.opandares.user.domain.gateway.AuthenticationGateway;
import com.opandares.user.domain.model.user.UserRepository;
import com.opandares.user.domain.model.user.User;
import com.opandares.user.infrastructure.entity.user.UserEntity;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserUseCase {

    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final AuthenticationGateway authenticationGateway;

    private UserEntity toEntity(User user) {
        try{
            return UserEntity.builder()
                    .name(user.getName())
                    .email(user.getEmail())
                    .password(user.getPassword())
                    .phones(objectMapper.writeValueAsString(user.getPhones()))
                    .created(new Timestamp(System.currentTimeMillis()))
                    .modified(new Timestamp(System.currentTimeMillis()))
                    .lastLogin(new Timestamp(System.currentTimeMillis()))
                    .isActive(true)
                    .token(user.getToken())
                    .build();
        }catch (JsonProcessingException e){
            throw new RuntimeException("Phones could not be processed");
        }

    }

    public UserUseCase(UserRepository userRepository, ObjectMapper objectMapper, AuthenticationGateway authenticationGateway) {
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
        this.authenticationGateway = authenticationGateway;
    }

    public User createUser(User user){

        if (!validEmail(user.getEmail()))
            throw new InvalidEmailException();

        UserEntity userEntity = userRepository.findByEmail(user.getEmail());

        if (Objects.nonNull(userEntity))
            throw new UserExistException();

        Optional.ofNullable(user.getEmail())
                .map(authenticationGateway::authorize)
                .ifPresent(user::setToken);
        userRepository.save(this.toEntity(user));

        return user;
    }

    private Boolean validEmail (String email) {
        Pattern pattern = Pattern.compile("^([0-9a-zA-Z]+[-._+&])*[0-9a-zA-Z]+@([-0-9a-zA-Z]+[.])+[a-zA-Z]{2,6}$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
