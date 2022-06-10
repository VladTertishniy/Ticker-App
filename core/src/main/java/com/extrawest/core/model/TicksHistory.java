package com.extrawest.core.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.Duration;
import java.util.Collection;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Document(collection = "ticksHistory")
public class TicksHistory {

    @Id
    private String id;
    private Date timestamp;
    private Side side;
    private Ticker relatedTicker;
    private Duration currentInterval;

}
