package com.opandares.user.infrastructure.entity.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private UUID id;
    private String name;
    private String email;
    private String password;
    private String phones;
    private Timestamp created;
    private Timestamp modified;
    private Timestamp lastLogin;
    private boolean isActive;
    private String token;

    public String setPhonesFromList(List<String> phones){
        return null;
    }
}
