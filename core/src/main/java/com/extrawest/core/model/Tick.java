package com.extrawest.core.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.Duration;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Document(collection = "ticks")
public class Tick {

    @MongoId
    private ObjectId id;
    private Side side;
    private Duration currentInterval;

}
