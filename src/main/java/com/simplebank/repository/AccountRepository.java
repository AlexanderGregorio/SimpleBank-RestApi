package com.simplebank.repository;

import com.simplebank.model.Account;
import com.simplebank.model.AccountId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account, AccountId> { }
