package com.extrawest.core.service.impl;

import com.extrawest.core.model.Ticker;
import com.extrawest.core.repository.TickerRepository;
import com.extrawest.core.repository.TickerStatisticRepository;
import com.extrawest.core.security.AuthenticationFacade;
import com.extrawest.core.service.StatisticService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@Data
public class StatisticServiceImpl implements StatisticService {

    private TickerStatisticRepository tickerStatisticRepository;
    private final AuthenticationFacade authenticationFacade;
    private final TickerServiceImpl tickerService;

    @Override
    public Map<Ticker, Integer> getTicksCountForPeriod(Date from, Date to) {
        List<Ticker> tickers = tickerService.findAllByOwner(getCurrentUserEmail());
        return tickerStatisticRepository.getTicksCountForPeriod(tickers, from, to);
    }

    @Override
    public Map<Ticker, Duration> getAverageTickTimeOut() {
        List<Ticker> tickers = tickerService.findAllByOwner(getCurrentUserEmail());
        return tickerStatisticRepository.getAverageTickTimeOut(tickers);
    }

    private String getCurrentUserEmail () {
        return authenticationFacade.getAuthentication().getName();
    }
}
