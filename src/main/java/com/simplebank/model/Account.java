package com.simplebank.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(AccountId.class)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long accountCode;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bankCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User owner;
    private BigDecimal balance;

    private String password;

    public Account(User owner, BigDecimal balance, String password) {
        this.owner = owner;
        this.balance = balance;
        this.password = password;
    }

}
