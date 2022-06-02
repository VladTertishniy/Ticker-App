package com.extrawest.core.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Document(collection = "roles")
public class Role {

    @MongoId
    private ObjectId id;
    private String name;

}
