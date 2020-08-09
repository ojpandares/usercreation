package com.opandares.user.domain.usescase.user;

import com.opandares.user.domain.exception.InvalidEmailException;
import com.opandares.user.domain.exception.InvalidFormatException;
import com.opandares.user.domain.exception.UserExistException;
import com.opandares.user.domain.exception.UserNotFoundException;
import com.opandares.user.domain.gateway.UserGateway;
import com.opandares.user.domain.model.user.User;
import com.opandares.user.infrastructure.adapter.UserServiceAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class UserUseCase {
    private static final Logger logger = LoggerFactory.getLogger(UserUseCase.class);
    private final UserGateway userGateway;

    public UserUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public User createUser(User user){
        validate(user);
        User response = userGateway.createdUser(user);
        if (Objects.isNull(response))
            throw new UserExistException();
        logger.info("User created {}",response);
        return response;
    }

    public User updateUser(User user){
        validate(user);
        User response = userGateway.updateUser(user);
        if (Objects.isNull(response))
            throw new UserNotFoundException();
        logger.info("Updated user {}",response);
        return response;
    }

    private void validate(User user){
        logger.info("Email user: {}",user.getEmail());
        if (!userGateway.validEmail(user.getEmail()))
            throw new InvalidEmailException();
        logger.info("Password user: {}",user.getPassword());
        if (!userGateway.validPassword(user.getPassword()))
            throw new InvalidFormatException();
    }
}
