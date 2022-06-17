package com.extrawest.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tickerSequence")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TickerSequence {
    @Id
    private String id;
    private int sequence;
}
