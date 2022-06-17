package com.extrawest.core.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Document(collection = "tickers")
public class Ticker {

    @Transient
    public static final String SEQUENCE = "tickerIdSequence";
    @Id
    private int id;
    private Status status;
    private double tickInterval;
    private Set<Tick> ticks;
    @DBRef()
    private User owner;

}
