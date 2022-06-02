package com.extrawest.core.controllers;

import com.extrawest.core.AuthenticationFacade;
import com.extrawest.core.model.Ticker;
import com.extrawest.core.model.User;
import com.extrawest.core.repository.TickerRepository;
import com.extrawest.core.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static java.time.temporal.ChronoUnit.SECONDS;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class TickerController {

    private AuthenticationFacade authenticationFacade;
    private TickerRepository tickerRepository;
    private UserRepository userRepository;

    @RequestMapping(path = "/createTicker/{tickInterval}", method = RequestMethod.POST)
    public ResponseEntity<?> createTicker (@PathVariable(name = "tickInterval") String tickInterval) {
        Ticker ticker = new Ticker();
        long duration = Long.parseLong(tickInterval);
        ticker.setTickInterval(Duration.of(duration, SECONDS));
        String email = authenticationFacade.getAuthentication().getName();
        User user = userRepository.getUserByEmail(email).get();
        ticker.setOwner(user);
        tickerRepository.save(ticker);
        return new ResponseEntity<>("test", HttpStatus.OK);
    }
}
