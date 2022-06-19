package com.extrawest.core.service.impl;

import com.extrawest.core.TickerFeignClient;
import com.extrawest.core.dto.TickerDTO;
import com.extrawest.core.dto.request.TickerRequestDTO;
import com.extrawest.core.dto.response.TickerResponseDTO;
import com.extrawest.core.dto.mapper.TickerMapper;
import com.extrawest.core.model.Status;
import com.extrawest.core.model.Tick;
import com.extrawest.core.model.TickStatistic;
import com.extrawest.core.model.Ticker;
import com.extrawest.core.repository.TickStatisticRepository;
import com.extrawest.core.repository.TickerRepository;
import com.extrawest.core.security.AuthenticationFacade;
import com.extrawest.core.service.TickerService;
import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.expression.AccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.nio.file.AccessDeniedException;
import java.util.Collection;
import java.util.Date;
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
    private final TickStatisticRepository tickStatisticRepository;
    private final TickerSequenceGeneratorService tickerSequenceGeneratorService;
    private final AuthenticationFacade  authenticationFacade;

    public void updateTickerTicks(Ticker ticker, Tick tick) {
        ticker.getTicks().add(tick);
        tickerRepository.save(ticker);
    }

    @Override
    public List<Ticker> findAllByOwner(String email) {
        return tickerRepository.findAllByOwner(userService.getUserByEmail(email).orElseThrow());
    }

    @Override
    public TickerResponseDTO createTicker(TickerDTO tickerDTO) {
        Ticker ticker = tickerMapper.tickerDTOToTicker(tickerDTO);
        ticker.setId(tickerSequenceGeneratorService.getSequenceNumber(Ticker.SEQUENCE));
        tickerRepository.save(ticker);
        return tickerMapper.tickerToTickerResponseDTO(ticker);
    }

    @Override
    public ResponseEntity<String> updateTickInterval(int id, TickerRequestDTO tickerRequestDTO) {
        Ticker ticker = tickerRepository.getTickerById(id).orElseThrow();
        if (checkThatCurrentUserIsOwner(ticker)) {
            ticker.setTickInterval(tickerRequestDTO.getInterval());
            tickerRepository.save(ticker);
            return ResponseEntity.status(HttpStatus.OK).body("Ticker interval updated");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Current user not owner, denied");
    }

    @Override
    public Optional<Ticker> getTickerById (int id) {
        return tickerRepository.getTickerById(id);
    }

    private void saveTickStatistic(Ticker ticker) {
        TickStatistic tickStatistic = new TickStatistic(new Date(),
                ticker.getOwner().getEmail(),
                ticker.getStatus(),
                ticker.getId());
        tickStatisticRepository.save(tickStatistic);
    }

    @Override
    public Collection<Ticker> getTickersByStatus (Status status) {
        return tickerRepository.getTickersByStatus(status);
    }

    @Override
    public ResponseEntity<String> startTicker (int id, boolean isRestarting) throws AccessException {
        Ticker ticker = tickerMapper.tickerIdToTicker(id);
        if (ticker != null) {
            if (checkThatCurrentUserIsOwner(ticker)) {
                if (Status.ACTIVE != ticker.getStatus() || isRestarting) {
                    try {
                        tickerFeignClient.start(tickerMapper.tickerToTickerFeignDTO(ticker));
                        updateTickerStatus(ticker, Status.ACTIVE);
                        saveTickStatistic(ticker);
                        return new ResponseEntity<>("Run ticker: "+ ticker, HttpStatus.OK);
                    } catch (FeignException e) {
                        log.error(e.getMessage());
                        return new ResponseEntity<>("Error while starting ticker: " + ticker, HttpStatus.BAD_REQUEST);
                    }
                }
            } else throw new AccessException("Can`t update the ticker, user is not owner");
        } return new ResponseEntity<>("Ticker with id: " + id + " not found", HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<String> stopTicker (int id) throws AccessException {
        Ticker ticker = tickerMapper.tickerIdToTicker(id);
        if (ticker != null) {
            if (checkThatCurrentUserIsOwner(ticker)) {
                try {
                    tickerFeignClient.stop(tickerMapper.tickerToTickerFeignDTO(ticker));
                    updateTickerStatus(ticker, Status.PAUSED);
                    saveTickStatistic(ticker);
                    return new ResponseEntity<>("Ticker with id: " + id + " stopped", HttpStatus.OK);
                } catch (FeignException e) {
                    log.error(e.getMessage());
                    return new ResponseEntity<>("Error while stopping ticker: " + ticker, HttpStatus.BAD_REQUEST);
                }
            } else throw new AccessException("Can`t update the ticker, user is not owner");
        } return new ResponseEntity<>("Ticker with id: " + id + " not found", HttpStatus.NOT_FOUND);
    }

    @Override
    public void updateTickerStatus(Ticker ticker, Status status) throws AccessException {
        if (checkThatCurrentUserIsOwner(ticker)) {
            ticker.setStatus(status);
            tickerRepository.save(ticker);
        } else throw new AccessException("Can`t update the ticker, user is not owner");
    }

    private boolean checkThatCurrentUserIsOwner (Ticker ticker) {
        return authenticationFacade.getAuthentication().getName().equals(ticker.getOwner().getEmail());
    }

    @PostConstruct
    private void init() throws AccessException {
        Collection<Ticker> activeTickersList = tickerRepository.findAllByStatus(Status.ACTIVE);
        for (Ticker tick : activeTickersList) {
            if (Status.ACTIVE == tick.getStatus()) {
                startTicker(tick.getId(), true);
            }
        }
    }
}
