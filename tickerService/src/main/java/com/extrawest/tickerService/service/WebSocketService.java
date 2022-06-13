package com.extrawest.tickerService.service;

import com.extrawest.tickerService.model.TickSender;

public interface WebSocketService {
    void sendToPublic(TickSender tickSender);
    void sendToUser(String userName, TickSender tickSender);
}
