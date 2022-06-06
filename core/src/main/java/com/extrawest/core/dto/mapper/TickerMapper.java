package com.extrawest.core.dto.mapper;

import com.extrawest.core.dto.TickerDTO;
import com.extrawest.core.model.Status;
import com.extrawest.core.model.Ticker;
import com.extrawest.core.model.User;
import com.extrawest.core.repository.TickerRepository;
import com.extrawest.core.repository.UserRepository;
import com.extrawest.core.security.AuthenticationFacade;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;

import static java.time.temporal.ChronoUnit.SECONDS;

@Component
@AllArgsConstructor
public class TickerMapper {
    private final AuthenticationFacade authenticationFacade;
    private final UserRepository userRepository;

    public Ticker tickerDTOToTicker(TickerDTO tickerDTO) {
        Ticker ticker = new Ticker();
        long duration = tickerDTO.getInterval();
        ticker.setTickInterval(Duration.of(duration, SECONDS));
        String email = authenticationFacade.getAuthentication().getName();
        User user = userRepository.getUserByEmail(email).get();
        ticker.setOwner(user);
        ticker.setStatus(Status.NEW);
        return ticker;
    }

}
