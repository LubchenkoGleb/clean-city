package com.kpi.diploma.smartroads.model.document.user;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "user")
public class Driver extends User {

    private String inviteUrl;

    private String firstName;

    private String lastName;
}
