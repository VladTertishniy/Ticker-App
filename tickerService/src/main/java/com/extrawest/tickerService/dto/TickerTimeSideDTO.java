package com.extrawest.tickerService.dto;

import com.extrawest.tickerService.model.Side;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TickerTimeSideDTO {
    private Date timestamp;
    private Side side;
}
