package com.extrawest.core.dto.feign;

import lombok.Data;
import org.bson.types.ObjectId;

import java.time.Duration;

@Data
public class TickerFeignDTO {
    private ObjectId id;
    private Duration interval;
    private String userEmail;
}
