package com.extrawest.tickerService.controller;

import com.extrawest.tickerService.dto.TickerRequestDTO;
import com.extrawest.tickerService.service.TickerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/tickers")
@AllArgsConstructor
public class TickerController {

    private final TickerService tickerService;

    @PostMapping("/start")
    public void start(@RequestBody TickerRequestDTO request) {
        tickerService.start(request);
    }

    @PostMapping("/stop")
    public void stop(@RequestBody TickerRequestDTO request) {
        tickerService.stop(request);
    }
}
