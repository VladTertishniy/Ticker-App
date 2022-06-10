package com.extrawest.tickerService.storage;

import com.extrawest.tickerService.dto.TickerTimeSideDTO;
import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class TickStorage {
    public static final Map<String, TickerTimeSideDTO> lastTickMap = new HashMap<>();
}
