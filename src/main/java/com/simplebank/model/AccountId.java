package com.simplebank.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountId implements Serializable {

    private Long accountCode;
    private Long bankCode;

}
