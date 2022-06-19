package com.extrawest.core.service;

import com.extrawest.core.dto.TickerDTO;
import com.extrawest.core.dto.request.TickerRequestDTO;
import com.extrawest.core.dto.response.TickerResponseDTO;
import com.extrawest.core.model.Status;
import com.extrawest.core.model.Tick;
import com.extrawest.core.model.Ticker;
import org.springframework.expression.AccessException;
import org.springframework.http.ResponseEntity;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface TickerService {
    void updateTickerTicks(Ticker ticker, Tick tick);
    TickerResponseDTO createTicker(TickerDTO tickerDTO);
    ResponseEntity<String> updateTickInterval(int id, TickerRequestDTO tickerRequestDTO);
    Optional<Ticker> getTickerById (int id);
    Collection<Ticker> getTickersByStatus (Status status);
    ResponseEntity<String> startTicker (int id) throws AccessException;
    ResponseEntity<String> stopTicker (int id) throws AccessException;
    void updateTickerStatus(Ticker ticker, Status status) throws AccessException;
    List<Ticker> findAllByOwner(String email);

}
