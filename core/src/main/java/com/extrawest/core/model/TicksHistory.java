package com.extrawest.core.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Duration;
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
    @DBRef()
    private Ticker ticker;
    private Duration currentInterval;

}
