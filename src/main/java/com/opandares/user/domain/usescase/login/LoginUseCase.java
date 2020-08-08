package com.opandares.user.domain.usescase.login;

import com.opandares.user.domain.exception.BusinessException;
import com.opandares.user.domain.exception.InvalidEmailException;
import com.opandares.user.domain.exception.UserIsNullExeption;
import com.opandares.user.domain.gateway.UserGateway;
import com.opandares.user.domain.model.user.LoginRequest;
import com.opandares.user.domain.model.user.User;

import java.util.Objects;

public class LoginUseCase {

    private final UserGateway userGateway;

    public LoginUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public User login(LoginRequest loginRequest){

        validate(loginRequest);
        return userGateway.authenticate(loginRequest.getEmail(),loginRequest.getPassword());
    }

    private void validate(LoginRequest loginRequest){

        if (Objects.isNull(loginRequest))
            throw new UserIsNullExeption();

        if (!userGateway.validEmail(loginRequest.getEmail()))
            throw new InvalidEmailException();

        if(Objects.isNull(loginRequest.getPassword()) || loginRequest.getPassword().isEmpty())
            throw new BusinessException();
    }
}
