package com.extrawest.core.dto.feign;

import lombok.Data;

@Data
public class TickerFeignDTO {
    private String tickerId;
    private double interval;
    private String userEmail;
}
