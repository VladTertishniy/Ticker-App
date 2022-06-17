package com.extrawest.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "userSequence")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSequence {
    @Id
    private String id;
    private int sequence;
}
