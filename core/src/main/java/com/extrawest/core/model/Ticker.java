package com.extrawest.core.model;

import lombok.*;
import org.springframework.data.annotation.Id;
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

    @Id
    private String id;
    private Status status;
    private double tickInterval;
    private Set<Tick> ticks;
    @DBRef()
    private User owner;

}
