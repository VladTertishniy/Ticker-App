package com.extrawest.tickerService.service;

import com.extrawest.tickerService.dto.TickerRequestDTO;

public interface TickerService {
    void start(TickerRequestDTO request, String secretKey);
    void stop(TickerRequestDTO request, String secretKey);
}
