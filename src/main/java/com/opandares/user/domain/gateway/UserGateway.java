package com.opandares.user.domain.gateway;

import com.opandares.user.domain.model.user.User;

public interface UserGateway {

    User authenticate (String email, String password);

    boolean validEmail (String email);

    boolean validPassword(String password);

    User createdUser (User user);

    User updateUser (User user);
}
