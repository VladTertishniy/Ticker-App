package com.extrawest.tickerService.storage;

import lombok.experimental.UtilityClass;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@UtilityClass
public class TasksStorage {
    public static final Map<Integer, ScheduledFuture<?>> scheduleStorageMap = new ConcurrentHashMap<>();
}
