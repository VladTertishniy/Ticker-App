package com.extrawest.core.controllers;

import com.extrawest.core.dto.TickerDTO;
import com.extrawest.core.dto.response.TickerResponseDTO;
import com.extrawest.core.model.Ticker;
import com.extrawest.core.service.impl.TickerServiceImpl;
import com.extrawest.core.utility.PathConstants;
import lombok.AllArgsConstructor;
import org.springframework.expression.AccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(PathConstants.API_PATH)
@AllArgsConstructor
public class TickerController {

    private TickerServiceImpl tickerService;

    @PostMapping(path = "/createTicker/")
    public ResponseEntity<TickerResponseDTO> createTicker (@RequestBody @Valid TickerDTO tickerDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(tickerService.createTicker(tickerDTO));
    }

    @GetMapping(path = "/getTickerById/{id}")
    public ResponseEntity<Ticker> getTickerById(@PathVariable int id) {
        return new ResponseEntity<>(tickerService.getTickerById(id).orElseThrow(), HttpStatus.OK);
    }

    @GetMapping(path = "/startTickerById/{id}")
    public ResponseEntity<String> startTickerById (@PathVariable int id) throws AccessException {
        return tickerService.startTicker(id, false);
    }

    @GetMapping(path = "/stopTickerById/{id}")
    public ResponseEntity<String> stopTickerById (@PathVariable int id) throws AccessException {
        return tickerService.stopTicker(id);
    }
}
