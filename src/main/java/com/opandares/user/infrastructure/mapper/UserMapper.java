package com.opandares.user.infrastructure.mapper;

import com.opandares.user.domain.model.user.User;
import com.opandares.user.infrastructure.entity.user.UserEntity;

public interface UserMapper {

    UserEntity toEntity(User user);

    User toModel(UserEntity userEntity);
}
