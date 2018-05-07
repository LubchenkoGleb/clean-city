package com.kpi.diploma.smartroads.model.document.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "user")
@ToString(exclude = {"boss"}, callSuper = true)
@EqualsAndHashCode(exclude = {"boss"}, callSuper = true)
public class Driver extends User {

    private String inviteKey;

    private String firstName;

    private String lastName;

    @DBRef(lazy = true)
    @JsonIgnore
    private User boss;
}
