package com.extrawest.core.dto.mapper;

import com.extrawest.core.dto.TickerDTO;
import com.extrawest.core.dto.feign.TickerFeignDTO;
import com.extrawest.core.model.Status;
import com.extrawest.core.model.Tick;
import com.extrawest.core.model.Ticker;
import com.extrawest.core.model.User;
import com.extrawest.core.repository.TickerRepository;
import com.extrawest.core.repository.UserRepository;
import com.extrawest.core.security.AuthenticationFacade;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static java.time.temporal.ChronoUnit.SECONDS;

@Component
@AllArgsConstructor
public class TickerMapper {
    private final AuthenticationFacade authenticationFacade;
    private final UserRepository userRepository;
    private final TickerRepository tickerRepository;

    public Ticker tickerDTOToTicker(TickerDTO tickerDTO) {
        Set<Tick> ticks = new HashSet<>();
        Ticker ticker = new Ticker();
        long duration = tickerDTO.getInterval();
        ticker.setTickInterval(Duration.of(duration, SECONDS));
        String email = authenticationFacade.getAuthentication().getName();
        User user = userRepository.getUserByEmail(email).get();
        ticker.setOwner(user);
        ticker.setStatus(Status.NEW);
        ticker.setTicks(ticks);
        return ticker;
    }

    public Ticker tickerIdToTicker(String id) {
        Optional<Ticker> ticker = tickerRepository.getTickerById(id);
        return ticker.orElse(null);
    }

    public TickerFeignDTO tickerToTickerFeignDTO (Ticker ticker) {
        TickerFeignDTO tickerFeignDTO = new TickerFeignDTO();
        tickerFeignDTO.setTickerId(ticker.getId());
        tickerFeignDTO.setInterval(ticker.getTickInterval());
        tickerFeignDTO.setUserEmail(ticker.getOwner().getEmail());
        return tickerFeignDTO;
    }

}
