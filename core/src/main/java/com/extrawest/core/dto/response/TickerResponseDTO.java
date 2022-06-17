package com.extrawest.core.dto.response;

import com.extrawest.core.model.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TickerResponseDTO {
    private int id;
    private Status status;
    private double tickInterval;
}
