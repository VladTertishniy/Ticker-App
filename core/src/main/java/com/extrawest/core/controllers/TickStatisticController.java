package com.extrawest.core.controllers;

import com.extrawest.core.model.Ticker;
import com.extrawest.core.service.impl.StatisticServiceImpl;
import com.extrawest.core.utility.PathConstants;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping(PathConstants.STATISTIC_PATH)
@AllArgsConstructor
public class TickStatisticController {

    private StatisticServiceImpl statisticService;

    @GetMapping("/countForPeriod")
    public ResponseEntity<Map<Ticker, Integer>> getTicksCountForPeriod (
            @RequestParam @DateTimeFormat(pattern = "dd.MM.yyyy-HH:mm:ss") Date from,
            @RequestParam @DateTimeFormat(pattern = "dd.MM.yyyy-HH:mm:ss") Date to
    ) {
        return ResponseEntity.ok(statisticService.getTicksCountForPeriod(from, to));
    }

    @GetMapping("/averageTickTimeout")
    public ResponseEntity<Map<Ticker, Duration>> getAverageTickTimeOut () {
        return ResponseEntity.ok(statisticService.getAverageTickTimeOut());
    }

}
