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
    private final WebSocketService webSocketService;

    public void send(TickResponseDTO response) {
        TickSender tickSender = new TickSender(response.toString());
        webSocketService.sendToPublic(tickSender);
        webSocketService.sendToUser(response.getUserEmail(), tickSender);
        kafkaTemplate.send("ticks", response);
    }

}
