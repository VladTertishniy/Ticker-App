package com.extrawest.core.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Document(collection = "ticksHistory")
public class TicksHistory {

    private ObjectId id;
    private Date timestamp;
    private Side side;
    private int currentInterval;

}
