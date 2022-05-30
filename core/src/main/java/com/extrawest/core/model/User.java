package com.extrawest.core.model;

import lombok.*;

import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class User {

    private int id;
    private String email;
    private String password;
    private ArrayList<Ticker> tickers;

}
