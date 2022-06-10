package com.extrawest.tickerService.service;

import com.extrawest.tickerService.dto.TickResponseDTO;
import com.extrawest.tickerService.model.TickSender;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class KafkaProducerService {
    private final KafkaTemplate<Long, Object> kafkaTemplate;

    public void send(TickResponseDTO response) {
        kafkaTemplate.send("ticks", response);
    }

}
