package com.opandares.user.infrastructure.data;

import com.opandares.user.domain.model.user.User;
import com.opandares.user.infrastructure.entity.user.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, UUID> {

    UserEntity findByEmail(String email);

}
