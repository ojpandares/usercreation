package com.opandares.user.infrastructure.controller;

import com.opandares.user.domain.model.user.User;
import com.opandares.user.domain.usescase.user.UserUseCase;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserUseCase userUseCase;

    @PostMapping
    public User save(@RequestBody User user){
        return userUseCase.createUser(user);
    }

    @PutMapping
    public User updateUser(@RequestBody User user){
        logger.info("User to update {}",user);
        return userUseCase.updateUser(user);
    }
}
