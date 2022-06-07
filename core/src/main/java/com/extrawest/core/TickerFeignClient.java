package com.extrawest.core;

import com.extrawest.core.configuration.FeignConfig;
import com.extrawest.core.dto.feign.TickerFeignDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "tickerFeignClient",
url = "http://localhost:8080",
path = "/tickers",
configuration = FeignConfig.class)
public interface TickerFeignClient {

    @GetMapping("/start")
    void start(@RequestBody TickerFeignDTO tickerFeignDTO);

    @GetMapping("/stop")
    void stop(@RequestBody TickerFeignDTO tickerFeignDTO);

}
