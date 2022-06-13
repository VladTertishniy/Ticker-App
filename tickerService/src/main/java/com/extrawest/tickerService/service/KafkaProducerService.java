package com.extrawest.tickerService.service;

import com.extrawest.tickerService.dto.TickResponseDTO;

public interface KafkaProducerService {
    void send(TickResponseDTO response);
}
