package com.extrawest.core.dto.request;

import lombok.*;

import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TickerRequestDTO {
    @Min(value = 1)
    private int interval;
}
