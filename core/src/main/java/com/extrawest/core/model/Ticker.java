package com.extrawest.core.model;

import lombok.*;

import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Ticker {

    private int id;
    private Status status;
    private int tickInterval;
    private ArrayList ticks;

}
