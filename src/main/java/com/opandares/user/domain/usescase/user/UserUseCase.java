package com.opandares.user.domain.usescase.user;

import com.opandares.user.domain.exception.InvalidEmailException;
import com.opandares.user.domain.exception.InvalidFormatException;
import com.opandares.user.domain.exception.UserExistException;
import com.opandares.user.domain.gateway.UserGateway;
import com.opandares.user.domain.model.user.User;

import java.util.Objects;

public class UserUseCase {

    private final UserGateway userGateway;

    public UserUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public User createUser(User user){

        validate(user);
        User response = userGateway.createdUser(user);

        if (Objects.isNull(response))
            throw new UserExistException();

        return response;
    }

    public User updateUser(User user){

        validate(user);
        User response = userGateway.updateUser(user);

        if (Objects.isNull(response))
            throw new RuntimeException("El usuario no existe");

        return response;
    }

    private void validate(User user){

        if (!userGateway.validEmail(user.getEmail()))
            throw new InvalidEmailException();

        if (!userGateway.validPassword(user.getPassword()))
            throw new InvalidFormatException();

    }
}
