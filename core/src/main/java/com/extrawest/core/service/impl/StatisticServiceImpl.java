package com.extrawest.core.service.impl;

import com.extrawest.core.model.Status;
import com.extrawest.core.model.Ticker;
import com.extrawest.core.repository.TickerStatisticRepository;
import com.extrawest.core.repository.UserRepository;
import com.extrawest.core.security.AuthenticationFacade;
import com.extrawest.core.service.StatisticService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@Data
public class StatisticServiceImpl implements StatisticService {

    private TickerStatisticRepository tickerStatisticRepository;
    private UserRepository userRepository;
    private final AuthenticationFacade authenticationFacade;
    private final TickerServiceImpl tickerService;

    @Override
    public Map<Ticker, Integer> getTicksCountForPeriod(Date from, Date to) {
        List<Ticker> tickers = tickerService.findAllByOwner(getCurrentUserEmail());
        return tickerStatisticRepository.getTicksCountForPeriod(tickers, from, to);
    }

    @Override
    public Map<Ticker, Double> getAverageTickTimeOut() {
        List<Ticker> tickers = tickerService.findAllByOwner(getCurrentUserEmail());
        return tickerStatisticRepository.getAverageTickTimeOut(tickers);
    }

    @Override
    public Map<Status, Integer> getStartStopTickers() {
        return tickerStatisticRepository.getStartStopTickers(getCurrentUserEmail());
    }

    @Override
    public Map<Status, Integer> getActivePausedTickers() {
        return tickerStatisticRepository.getActivePausedTickers(userRepository.getUserByEmail(getCurrentUserEmail()).orElseThrow());
    }

    @Override
    public Map<Ticker, Integer> getLostTicks() {
        List<Ticker> tickers = tickerService.findAllByOwner(getCurrentUserEmail());
        return tickerStatisticRepository.getLostTicks(tickers);
    }

    private String getCurrentUserEmail () {
        return authenticationFacade.getAuthentication().getName();
    }
}
