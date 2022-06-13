package com.extrawest.core.service;

import com.extrawest.core.dto.TicksHistoryConsume;

public interface KafkaConsumerService {
    void consume(TicksHistoryConsume consume);

}
