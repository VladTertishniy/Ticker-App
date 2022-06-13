package com.extrawest.tickerService.service;

import com.extrawest.tickerService.dto.TickerRequestDTO;

public interface TickerService {
    void start(TickerRequestDTO request);
    void stop(TickerRequestDTO request);
}
