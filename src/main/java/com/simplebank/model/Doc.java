package com.simplebank.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
public class Doc {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fromUser;
    private String toUser;

    private BigDecimal value;
    private String currency;

    public Doc(String fromUser, String toUser, BigDecimal value, String currency) {
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.value = value;
        this.currency = currency;
    }

}
