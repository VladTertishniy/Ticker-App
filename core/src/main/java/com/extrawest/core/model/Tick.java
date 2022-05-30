package com.extrawest.core.model;

import lombok.*;

import java.time.Duration;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Tick {

    private int id;
    private Side side;
    private Duration currentInterval;

}
