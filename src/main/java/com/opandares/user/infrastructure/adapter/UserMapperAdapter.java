package com.opandares.user.infrastructure.adapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opandares.user.domain.exception.PhoneException;
import com.opandares.user.domain.model.user.User;
import com.opandares.user.infrastructure.entity.user.UserEntity;
import com.opandares.user.infrastructure.mapper.UserMapper;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Optional;

public class UserMapperAdapter implements UserMapper {

    private ObjectMapper objectMapper;


    public UserMapperAdapter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public UserEntity toEntity(User user){
        try{
            return UserEntity.builder()
                    .name(user.getName())
                    .email(user.getEmail())
                    .password(user.getPassword())
                    .phones(objectMapper.writeValueAsString(user.getPhones()))
                    .created(Optional.ofNullable(user.getCreated()).orElse(new Timestamp(System.currentTimeMillis())))
                    .modified(Optional.ofNullable(user.getModified()).orElse(new Timestamp(System.currentTimeMillis())))
                    .lastLogin(Optional.ofNullable(user.getLastLogin()).orElse(new Timestamp(System.currentTimeMillis())))
                    .isActive(true)
                    .token(user.getToken())
                    .build();
        }catch (JsonProcessingException e){
            throw new PhoneException();
        }
    }

    public User toModel(UserEntity userEntity){
        try {
            return User.builder()
                    .id(userEntity.getId())
                    .name(userEntity.getName())
                    .email(userEntity.getEmail())
                    .password(userEntity.getPassword())
                    .phones(objectMapper.readValue(userEntity.getPhones(), ArrayList.class))
                    .created(Optional.ofNullable(userEntity.getCreated()).orElse(new Timestamp(System.currentTimeMillis())))
                    .modified(Optional.ofNullable(userEntity.getModified()).orElse(new Timestamp(System.currentTimeMillis())))
                    .lastLogin(Optional.ofNullable(userEntity.getLastLogin()).orElse(new Timestamp(System.currentTimeMillis())))
                    .token(userEntity.getToken())
                    .isActive(true)
                    .build();
        }catch(JsonProcessingException e){
            throw new PhoneException();
        }
    }
}
