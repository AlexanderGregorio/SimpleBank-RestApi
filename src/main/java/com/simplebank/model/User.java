package com.simplebank.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class User {

    @Id
    private String documentCode;
    private String name;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Account> accounts;

    public User(String documentCode, String name) {
        this.documentCode = documentCode;
        this.name = name;
        this.accounts = new ArrayList<>();
    }

}
