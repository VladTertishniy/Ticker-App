package com.extrawest.core.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.ArrayList;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Document(collection = "users")
public class User {

    @MongoId
    private ObjectId id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private ArrayList<Ticker> tickers;
    @DBRef()
    private Set<Role> roles;

}
