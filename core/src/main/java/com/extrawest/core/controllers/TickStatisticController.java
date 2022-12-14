package com.extrawest.core.controllers;

import com.extrawest.core.model.Status;
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

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping(PathConstants.STATISTIC_PATH)
@AllArgsConstructor
public class TickStatisticController {

    private StatisticServiceImpl statisticService;

    @GetMapping("/countForPeriod")
    public ResponseEntity<Map<Ticker, Integer>> getTicksCountForPeriod(
            @RequestParam @DateTimeFormat(pattern = "dd.MM.yyyy-HH:mm:ss") Date from,
            @RequestParam @DateTimeFormat(pattern = "dd.MM.yyyy-HH:mm:ss") Date to
    ) {
        return ResponseEntity.ok(statisticService.getTicksCountForPeriod(from, to));
    }

    @GetMapping("/averageTickTimeout")
    public ResponseEntity<Map<Ticker, Double>> getAverageTickTimeOut() {
        return ResponseEntity.ok(statisticService.getAverageTickTimeOut());
    }

    @GetMapping("/startStop")
    public ResponseEntity<Map<Status, Integer>> getStartStopStatistic() {
        return ResponseEntity.ok(statisticService.getStartStopTickers());
    }

    @GetMapping("/activePaused")
    public ResponseEntity<Map<Status, Integer>> getActivePausedTickers() {
        return ResponseEntity.ok(statisticService.getActivePausedTickers());
    }

    @GetMapping("/lostTicks")
    public ResponseEntity<Map<Ticker, Integer>> getLostTicks() {
        return ResponseEntity.ok(statisticService.getLostTicks());
    }

}
