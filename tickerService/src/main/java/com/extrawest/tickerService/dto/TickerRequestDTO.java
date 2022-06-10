package com.extrawest.tickerService.dto;

import com.extrawest.tickerService.model.Side;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TickerRequestDTO {
    private String tickerId;
    private Date timestamp;
    private Side side;
    private Duration interval;
    private String userEmail;
}
