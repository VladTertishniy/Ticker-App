package com.extrawest.core.service;

import com.extrawest.core.dto.TickerDTO;
import com.extrawest.core.dto.mapper.TickerMapper;
import com.extrawest.core.security.AuthenticationFacade;
import com.extrawest.core.model.Ticker;
import com.extrawest.core.model.User;
import com.extrawest.core.repository.TickerRepository;
import com.extrawest.core.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.SECONDS;

@Service
@AllArgsConstructor
public class TickerService {

    private final AuthenticationFacade authenticationFacade;
    private final TickerRepository tickerRepository;
    private final UserRepository userRepository;

    private final TickerMapper tickerMapper;

    public void createTicker(TickerDTO tickerDTO) {
        tickerRepository.save(tickerMapper.tickerDTOToTicker(tickerDTO));
    }

    public Optional<Ticker> getTickerById (ObjectId id) {
        return tickerRepository.getTickerById(id);
    }
}
