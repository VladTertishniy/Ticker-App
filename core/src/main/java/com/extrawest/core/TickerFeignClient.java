package com.extrawest.core;

import com.extrawest.core.dto.feign.TickerFeignDTO;
import com.extrawest.core.utility.PathConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "tickerFeignClient",
url = "http://localhost:8080",
path = "/tickers")
public interface TickerFeignClient {

    @GetMapping(PathConstants.START_PATH)
    void start(@RequestBody TickerFeignDTO tickerFeignDTO);

    @GetMapping(PathConstants.STOP_PATH)
    void stop(@RequestBody TickerFeignDTO tickerFeignDTO);

}
