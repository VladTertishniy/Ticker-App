package com.extrawest.core.model;

import lombok.*;

import java.time.Duration;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Ticker {

    private int id;
    private Status status;
    private Duration tickInterval;
    private Set<Tick> ticks;
    private User owner;
    private boolean isEnabled;

}
