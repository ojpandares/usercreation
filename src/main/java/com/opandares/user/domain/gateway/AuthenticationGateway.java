package com.opandares.user.domain.gateway;

public interface AuthenticationGateway {

    String authorize(String email);
}
