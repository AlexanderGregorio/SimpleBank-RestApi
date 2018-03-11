package com.simplebank.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Doc {

    private String fromUser;
    private String toUser;
    private BigDecimal value;
    private String currency;

}
