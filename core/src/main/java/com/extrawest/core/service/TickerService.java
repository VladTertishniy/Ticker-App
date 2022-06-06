package com.extrawest.core.service;

import com.extrawest.core.security.AuthenticationFacade;
import com.extrawest.core.model.Ticker;
import com.extrawest.core.model.User;
import com.extrawest.core.repository.TickerRepository;
import com.extrawest.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;

import static java.time.temporal.ChronoUnit.SECONDS;

@Service
public class TickerService {

    private final AuthenticationFacade authenticationFacade;
    private final TickerRepository tickerRepository;
    private final UserRepository userRepository;

    public TickerService(@Autowired AuthenticationFacade authenticationFacade, @Autowired TickerRepository tickerRepository, @Autowired  UserRepository userRepository) {
        this.authenticationFacade = authenticationFacade;
        this.tickerRepository = tickerRepository;
        this.userRepository = userRepository;
    }

    public void createTicker(String tickInterval) {
        Ticker ticker = new Ticker();
        long duration = Long.parseLong(tickInterval);
        ticker.setTickInterval(Duration.of(duration, SECONDS));
        String email = authenticationFacade.getAuthentication().getName();
        User user = userRepository.getUserByEmail(email).get();
        ticker.setOwner(user);
        tickerRepository.save(ticker);
    }
}
