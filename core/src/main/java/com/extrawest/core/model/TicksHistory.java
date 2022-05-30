package com.extrawest.core.model;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class TicksHistory {

    private int id;
    private Date timestamp;
    private Side side;
    private int currentInterval;

}
