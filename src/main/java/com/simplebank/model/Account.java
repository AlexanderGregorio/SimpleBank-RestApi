package com.simplebank.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(AccountId.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "accountCode")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long accountCode;

    @Id
    private Long bankCode;

    @ManyToOne
    @JoinColumn(name = "user_documentCode")
    private User owner;

    private BigDecimal balance;

    private String password;

    public Account(Long bankCode, User owner, BigDecimal balance, String password) {
        this.bankCode = bankCode;
        this.owner = owner;
        this.balance = balance;
        this.password = password;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAccountCode(), getBankCode());
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Account)) return false;
        Account account = (Account) obj;
        return this.accountCode.equals(account.getAccountCode()) &&
                this.bankCode.equals(account.getBankCode());
    }
}
