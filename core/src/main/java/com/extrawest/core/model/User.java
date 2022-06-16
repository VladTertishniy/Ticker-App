package com.extrawest.core.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Document(collection = "users")
public class User {

    @Id
    private String id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private ArrayList<Ticker> tickers;
    @DBRef()
    private Set<Role> roles;

}
