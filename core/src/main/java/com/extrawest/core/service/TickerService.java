package com.extrawest.core.service;

import com.extrawest.core.dto.TickerDTO;
import com.extrawest.core.model.Status;
import com.extrawest.core.model.Ticker;
import org.springframework.http.ResponseEntity;

import java.util.Collection;
import java.util.Optional;

public interface TickerService {
    void saveTicker(Ticker ticker);
    ResponseEntity<?> createTicker(TickerDTO tickerDTO);
    Optional<Ticker> getTickerById (String id);
    Collection<Ticker> getTickersByStatus (Status status);
    ResponseEntity<?> startTicker (String id, boolean isRestarting);
    ResponseEntity<?> stopTicker (String id);
    void updateTickerStatus(Ticker ticker, Status status);

}
