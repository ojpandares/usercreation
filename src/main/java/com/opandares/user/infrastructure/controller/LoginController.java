package com.opandares.user.infrastructure.controller;

import com.opandares.user.domain.model.user.LoginRequest;
import com.opandares.user.domain.model.user.User;
import com.opandares.user.domain.usescase.login.LoginUseCase;
import com.opandares.user.infrastructure.adapter.UserServiceAdapter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("login")
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private final LoginUseCase loginUseCase;

    @PostMapping
    public User doLogin(@RequestBody LoginRequest loginRequest){
        return loginUseCase.login(loginRequest);
    }
}
