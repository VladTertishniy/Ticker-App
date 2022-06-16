package com.extrawest.core.service;

import com.extrawest.core.model.Ticker;;

import java.util.Date;
import java.util.Map;

public interface StatisticService {
    Map<Ticker, Integer> getTicksCountForPeriod(Date from, Date to);
    Map<Ticker, Double> getAverageTickTimeOut ();
}
