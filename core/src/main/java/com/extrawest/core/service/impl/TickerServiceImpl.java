package com.extrawest.core.service.impl;

import com.extrawest.core.TickerFeignClient;
import com.extrawest.core.dto.TickerDTO;
import com.extrawest.core.dto.mapper.TickerMapper;
import com.extrawest.core.model.Status;
import com.extrawest.core.model.Ticker;
import com.extrawest.core.repository.TickerRepository;
import com.extrawest.core.service.TickerService;
import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@EnableScheduling
@Slf4j
public class TickerServiceImpl implements TickerService {

    private final TickerRepository tickerRepository;

    private final TickerMapper tickerMapper;
    private final TickerFeignClient tickerFeignClient;
    private final UserServiceImpl userService;

    public void saveTicker(Ticker ticker) {
        tickerRepository.save(ticker);
    }

    @Override
    public List<Ticker> findAllByOwner(String email) {
        return tickerRepository.findAllByOwner(userService.getUserByEmail(email).get());
    }

    @Override
    public ResponseEntity<?> createTicker(TickerDTO tickerDTO) {
        tickerRepository.save(tickerMapper.tickerDTOToTicker(tickerDTO));
        return new ResponseEntity<>("Ticker created", HttpStatus.OK);
    }

    @Override
    public Optional<Ticker> getTickerById (String id) {
        return tickerRepository.getTickerById(id);
    }

    @Override
    public Collection<Ticker> getTickersByStatus (Status status) {
        return tickerRepository.getTickersByStatus(status);
    }

    @Override
    public ResponseEntity<?> startTicker (String id, boolean isRestarting) {
        Ticker ticker = tickerMapper.tickerIdToTicker(id);
        if (ticker != null) {
            if (Status.ACTIVE != ticker.getStatus() || isRestarting) {
                try {
                    tickerFeignClient.start(tickerMapper.tickerToTickerFeignDTO(ticker));
                    updateTickerStatus(ticker, Status.ACTIVE);
                    return new ResponseEntity<>("Run ticker: "+ ticker, HttpStatus.OK);
                } catch (FeignException e) {
                    log.error(e.getMessage());
                    return new ResponseEntity<>("Error while starting ticker: " + ticker, HttpStatus.BAD_REQUEST);
                }
            }
        } return new ResponseEntity<>("Ticker with id: " + id + " not found", HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<?> stopTicker (String id) {
        Ticker ticker = tickerMapper.tickerIdToTicker(id);
        if (ticker != null) {
            try {
                tickerFeignClient.stop(tickerMapper.tickerToTickerFeignDTO(ticker));
                updateTickerStatus(ticker, Status.DISABLED);
                return new ResponseEntity<>("Ticker with id: " + id + " stopped", HttpStatus.OK);
            } catch (FeignException e) {
                log.error(e.getMessage());
                return new ResponseEntity<>("Error while stopping ticker: " + ticker, HttpStatus.BAD_REQUEST);
            }
        } return new ResponseEntity<>("Ticker with id: " + id + " not found", HttpStatus.NOT_FOUND);
    }

    @Override
    public void updateTickerStatus(Ticker ticker, Status status) {
        ticker.setStatus(status);
        tickerRepository.save(ticker);
    }

    @PostConstruct
    private void init() {
        Collection<Ticker> activeTickersList = tickerRepository.findAllByStatus(Status.ACTIVE);
        for (Ticker tick : activeTickersList) {
            if (Status.ACTIVE == tick.getStatus()) {
                startTicker(tick.getId(), true);
            }
        }
    } //todo от сюда это нужно убрать
}
