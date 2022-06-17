package com.extrawest.core.service;

import com.extrawest.core.dto.TickerDTO;
import com.extrawest.core.dto.TickerResponseDTO;
import com.extrawest.core.model.Status;
import com.extrawest.core.model.Tick;
import com.extrawest.core.model.Ticker;
import org.springframework.http.ResponseEntity;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface TickerService {
    void updateTickerTicks(Ticker ticker, Tick tick);
    TickerResponseDTO createTicker(TickerDTO tickerDTO);
    Optional<Ticker> getTickerById (int id);
    Collection<Ticker> getTickersByStatus (Status status);
    ResponseEntity<?> startTicker (int id, boolean isRestarting);
    ResponseEntity<?> stopTicker (int id);
    void updateTickerStatus(Ticker ticker, Status status);
    List<Ticker> findAllByOwner(String email);

}
