package com.opandares.user.domain.model.user;

import com.opandares.user.infrastructure.entity.user.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, UUID> {

    User save(User user);

    UserEntity findByEmail(String email);
}
