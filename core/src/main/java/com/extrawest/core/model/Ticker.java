package com.extrawest.core.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.Duration;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Document(collection = "tickers")
public class Ticker {

    @MongoId
    private ObjectId id;
    private Status status;
    private Duration tickInterval;
    private Set<Tick> ticks;
    @DBRef()
    private User owner;
    private boolean isEnabled;

}
