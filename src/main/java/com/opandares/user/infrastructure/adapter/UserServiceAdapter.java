package com.opandares.user.infrastructure.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opandares.user.domain.exception.*;
import com.opandares.user.domain.gateway.AuthenticationGateway;
import com.opandares.user.domain.gateway.UserGateway;
import com.opandares.user.domain.model.user.User;
import com.opandares.user.infrastructure.data.UserRepository;
import com.opandares.user.infrastructure.entity.user.UserEntity;
import com.opandares.user.infrastructure.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class UserServiceAdapter implements UserGateway {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceAdapter.class);
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthenticationGateway authenticationGateway;
    private final ObjectMapper objectMapper;

    public UserServiceAdapter(UserRepository userRepository, UserMapper userMapper,
                              AuthenticationGateway authenticationGateway,
                              ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.authenticationGateway = authenticationGateway;
        this.objectMapper = objectMapper;
    }

    public User createdUser(User user){
        UserEntity userEntity = userRepository.findByEmail(user.getEmail());
        if (Objects.nonNull(userEntity))
            return null;
        Optional.ofNullable(user.getEmail())
                .map(authenticationGateway::authorize)
                .ifPresent(user::setToken);
        return userMapper.toModel(userRepository.save(userMapper.toEntity(user)));
    }

    @Transactional
    public User updateUser(User user){
        try {
            String phones = objectMapper.writeValueAsString(user.getPhones());
             int count =  userRepository.updateUser(user.getEmail(),
                                                    user.isActive(),
                                                    new Timestamp(System.currentTimeMillis()),
                                                    user.getName(),
                                                    user.getPassword(),phones);
             if ( count == 1 ) {
                 return userMapper.toModel(userRepository.findByEmail(user.getEmail()));
             }
            return null;
        }catch(Exception e){
            throw new PhoneException();
        }
    }

    @Override
    public User authenticate(String email, String password) {
        logger.info("email: {}",email);
        logger.info("password: {}",password);
        UserEntity entity = userRepository.findByEmail(email);
        if(Objects.isNull(entity))
            throw new UserNotFoundException();
        if (!entity.getPassword().equals(password))
            throw new PasswordNotMatchException();
        return userMapper.toModel(entity);
    }

    public boolean validEmail(String email) {
        Pattern pattern = Pattern.compile("^([0-9a-zA-Z]+[-._+&])*[0-9a-zA-Z]+@([-0-9a-zA-Z]+[.])+[a-zA-Z]{2,6}$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean validPassword(String password){
        Pattern pattern = Pattern.compile("(^[A-Z][a-z]+(\\d){2}$)|(^[a-z]+[A-Z](\\d){2}$)|(^(\\d){2}[A-Z]{1}[a-z]+$)|(^[a-z]+(\\d){2}[A-Z])");
        Matcher matcher = pattern.matcher(password);
        return  matcher.matches();
    }
}
