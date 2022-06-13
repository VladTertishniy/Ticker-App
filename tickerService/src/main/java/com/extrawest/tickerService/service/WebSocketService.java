package com.extrawest.tickerService.service;

import com.extrawest.tickerService.model.TickSender;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WebSocketService {

    private final SimpMessagingTemplate simpMessagingTemplate;

    public void sendToPublic(TickSender tickSender) {
        simpMessagingTemplate.convertAndSend("/topic/public", tickSender);
    }

    public void sendToUser(String userName, TickSender tickSender) {
        simpMessagingTemplate.convertAndSendToUser(userName, "/topic/user", tickSender);
    }

}
