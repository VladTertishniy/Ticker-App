package com.extrawest.core.dto;

import com.extrawest.core.model.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TickerResponseDTO {
    private String id;
    private Status status;
    private double tickInterval;
}
