package com.extrawest.tickerService.service.impl;

import com.extrawest.tickerService.model.TickSender;
import com.extrawest.tickerService.service.WebSocketService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WebSocketServiceImpl implements WebSocketService {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void sendToPublic(TickSender tickSender) {
        simpMessagingTemplate.convertAndSend("/topic/public", tickSender);
    }

    @Override
    public void sendToUser(String userName, TickSender tickSender) {
        simpMessagingTemplate.convertAndSendToUser(userName, "/topic/user", tickSender);
    }

}
