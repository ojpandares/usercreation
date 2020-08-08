package com.opandares.user.infrastructure.controller;

import com.opandares.user.domain.model.user.User;
import com.opandares.user.domain.usescase.user.UserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {

    private final UserUseCase userUseCase;

    @PostMapping
    public User save(@RequestBody User user){

        return userUseCase.createUser(user);
    }

    @PutMapping
    public User updateUser(@RequestBody User user){

        return userUseCase.updateUser(user);
    }
}
