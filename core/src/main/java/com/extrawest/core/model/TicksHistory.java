package com.extrawest.core.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Collection;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Document(collection = "ticksHistory")
public class TicksHistory {

    @MongoId
    private ObjectId id;
    private Date timestamp;
    private Side side;
    private Ticker relatedTicker;
    private int currentInterval;

}
