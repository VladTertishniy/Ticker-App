package com.extrawest.core.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(value = "tickStatistic")
@NoArgsConstructor
@Data
public class TickStatistic {
    @Id
    private String id;
    private Date timestamp;
    private String userEmail;
    private int tickerId;
    private Status status;

    public TickStatistic(Date timestamp, String userEmail, Status status, int tickerId) {
        this.timestamp = timestamp;
        this.userEmail = userEmail;
        this.status = status;
        this.tickerId = tickerId;
    }
}
