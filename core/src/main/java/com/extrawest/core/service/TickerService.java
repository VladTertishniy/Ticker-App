package com.extrawest.core.service;

import com.extrawest.core.TickerFeignClient;
import com.extrawest.core.dto.TickerDTO;
import com.extrawest.core.dto.mapper.TickerMapper;
import com.extrawest.core.model.Status;
import com.extrawest.core.model.Ticker;
import com.extrawest.core.repository.TickerRepository;
import feign.FeignException;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TickerService {

    private final TickerRepository tickerRepository;

    private final TickerMapper tickerMapper;
    private final TickerFeignClient tickerFeignClient;

    public void createTicker(TickerDTO tickerDTO) {
        tickerRepository.save(tickerMapper.tickerDTOToTicker(tickerDTO));
    }

    public Optional<Ticker> getTickerById (ObjectId id) {
        return tickerRepository.getTickerById(id);
    }

    public Collection<Ticker> getTickersByStatus (Status status) {
        return tickerRepository.getTickersByStatus(status);
    }

    public void startTicker (ObjectId id) {
        Ticker ticker = tickerMapper.tickerIdToTicker(id);
        if (ticker != null) {
            if (Status.NEW == ticker.getStatus()) {
                updateTickerStatus(ticker, Status.ACTIVE);
                try {
                    tickerFeignClient.start(tickerMapper.tickerToTickerFeignDTO(ticker));
                    //todo add some log4j
                } catch (FeignException e) {
                    //todo add some log4j
                }
            }
        } else throw new RuntimeException(); //todo
    }

    public void updateTickerStatus(Ticker ticker, Status status) {
        ticker.setStatus(status);
        tickerRepository.save(ticker);
    }
}
