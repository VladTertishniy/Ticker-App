package com.extrawest.tickerService.controller;

import com.extrawest.tickerService.dto.TickerRequestDTO;
import com.extrawest.tickerService.service.TickerService;
import com.extrawest.tickerService.utility.PathConstants;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(PathConstants.TICKERS_PATH)
@AllArgsConstructor
public class TickerController {

    private static final String REQUEST_HEADER_VALUE = "secretKey";
    private final TickerService tickerService;

    @PostMapping(PathConstants.START_PATH)
    public void start(@RequestBody TickerRequestDTO request, @RequestHeader(value = REQUEST_HEADER_VALUE) String secretKey) {
        tickerService.start(request, secretKey);
    }

    @PostMapping(PathConstants.STOP_PATH)
    public void stop(@RequestBody TickerRequestDTO request, @RequestHeader(value = REQUEST_HEADER_VALUE) String secretKey) {
        tickerService.stop(request, secretKey);
    }
}
