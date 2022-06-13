package com.extrawest.tickerService.controller;

import com.extrawest.tickerService.dto.TickerRequestDTO;
import com.extrawest.tickerService.service.impl.TickerServiceImpl;
import com.extrawest.tickerService.utility.PathConstants;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(PathConstants.TICKERS_PATH)
@AllArgsConstructor
public class TickerController {

    private final TickerServiceImpl tickerService;

    @PostMapping(PathConstants.START_PATH)
    public void start(@RequestBody TickerRequestDTO request) {
        tickerService.start(request);
    }

    @PostMapping(PathConstants.STOP_PATH)
    public void stop(@RequestBody TickerRequestDTO request) {
        tickerService.stop(request);
    }
}
