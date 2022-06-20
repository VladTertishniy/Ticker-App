package com.extrawest.core.dto.mapper;

import com.extrawest.core.dto.TickerDTO;
import com.extrawest.core.model.Status;
import com.extrawest.core.model.Tick;
import com.extrawest.core.model.Ticker;
import com.extrawest.core.model.User;
import com.extrawest.core.repository.TickerRepository;
import com.extrawest.core.repository.UserRepository;
import com.extrawest.core.security.AuthenticationFacade;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Component
@AllArgsConstructor
public class TickerMapper {
    private final AuthenticationFacade authenticationFacade;
    private final UserRepository userRepository;

    public Ticker tickerDTOToTicker(TickerDTO tickerDTO) {
        Set<Tick> ticks = new HashSet<>();
        Ticker ticker = new Ticker();
        ticker.setTickInterval(tickerDTO.getInterval());
        String email = authenticationFacade.getAuthentication().getName();
        User user = userRepository.getUserByEmail(email).orElseThrow();
        ticker.setOwner(user);
        ticker.setStatus(Status.NEW);
        ticker.setTicks(ticks);
        return ticker;
    }
}
