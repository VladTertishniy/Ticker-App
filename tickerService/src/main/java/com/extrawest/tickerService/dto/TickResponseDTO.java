package com.extrawest.tickerService.dto;

import com.extrawest.tickerService.model.Side;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TickResponseDTO {
    private String tickerId;
    private Date timestamp;
    private Side side;
    private double currentInterval;
    private String userEmail;
}
