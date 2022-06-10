package com.extrawest.core.dto;

import com.extrawest.core.model.Side;
import lombok.Data;
import org.bson.types.ObjectId;

import java.time.Duration;
import java.util.Date;

@Data
public class TicksHistoryConsume {
    private String tickerId;
    private Date timestamp;
    private Side side;
    private Duration currentInterval;
    private String userEmail;
}
