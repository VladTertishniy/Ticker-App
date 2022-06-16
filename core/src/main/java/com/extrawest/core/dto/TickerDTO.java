package com.extrawest.core.dto;

import lombok.Data;

@Data
public class TickerDTO {
    private int id;
    private double interval;
    private String userEmail;
}
