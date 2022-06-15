package com.extrawest.core.service;

import com.extrawest.core.model.Ticker;
import org.springframework.http.ResponseEntity;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface StatisticService {
    Map<Ticker, Integer> getTicksCountForPeriod(Date from, Date to);
    Map<Ticker, Duration> getAverageTickTimeOut ();
}
