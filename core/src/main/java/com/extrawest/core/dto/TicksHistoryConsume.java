package com.extrawest.core.dto;

import com.extrawest.core.model.Side;
import lombok.Data;
import java.util.Date;

@Data
public class TicksHistoryConsume {
    private int tickerId;
    private Date timestamp;
    private Side side;
    private double currentInterval;
    private String userEmail;
}
