package com.opandares.user.domain.model.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.opandares.user.domain.model.phone.Phone;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class User {
    private String name;
    private String email;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String password;
    private List<Phone> phones;
    private Timestamp created;
    private Timestamp modified;
    @JsonProperty(value = "last_login")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Timestamp lastLogin;
    @JsonProperty(value = "isactive")
    private boolean isActive;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String token;

}
