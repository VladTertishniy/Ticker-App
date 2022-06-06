package com.extrawest.core.controllers;

import com.extrawest.core.dto.TickerDTO;
import com.extrawest.core.model.Ticker;
import com.extrawest.core.service.TickerService;
import com.extrawest.core.utility.PathConstants;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(PathConstants.API_PATH)
@AllArgsConstructor
public class TickerController {

    private TickerService tickerService;

    @PostMapping(path = "/createTicker/")
    public ResponseEntity<?> createTicker (@RequestBody TickerDTO tickerDTO) {
        tickerService.createTicker(tickerDTO);
        return new ResponseEntity<>("Ticker created", HttpStatus.OK);
    }

    @RequestMapping(path = "/getTickerById/{id}", method = RequestMethod.GET)
    public ResponseEntity<Ticker> getTickerById(@PathVariable ObjectId id) {
        return new ResponseEntity<>(tickerService.getTickerById(id).get(), HttpStatus.OK);
    }
}
