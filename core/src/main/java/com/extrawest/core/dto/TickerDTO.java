package com.extrawest.core.dto;

import lombok.Data;

@Data
public class TickerDTO {
    private int id;
    private long interval;
    private String userEmail;
}
