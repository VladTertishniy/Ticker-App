package com.extrawest.tickerService.storage;

import lombok.experimental.UtilityClass;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@UtilityClass
public class ScheduleTasksStorage {
    public static final Map<String, ScheduledFuture<?>> scheduleStorageMap = new ConcurrentHashMap<>();
}
