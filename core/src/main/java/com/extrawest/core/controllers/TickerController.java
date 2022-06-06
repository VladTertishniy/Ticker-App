package com.extrawest.core.controllers;

import com.extrawest.core.service.TickerService;
import com.extrawest.core.utility.PathConstants;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(PathConstants.API_PATH)
@AllArgsConstructor
public class TickerController {

    private TickerService tickerService;

    @RequestMapping(path = "/createTicker/{tickInterval}", method = RequestMethod.POST)
    public ResponseEntity<?> createTicker (@PathVariable(name = "tickInterval") String tickInterval) {
        tickerService.createTicker(tickInterval);
        return new ResponseEntity<>("test", HttpStatus.OK);
    }
}
