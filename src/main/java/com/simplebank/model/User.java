package com.simplebank.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "documentCode")
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

    @Override
    public int hashCode() {
        return documentCode.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof User)) return false;
        User user = (User) obj;
        return this.documentCode.equals(user.getDocumentCode());
    }
}
