package com.simplebank.api;

import com.simplebank.exception.ElementAlreadyExistException;
import com.simplebank.model.Account;
import com.simplebank.model.AccountId;
import com.simplebank.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

import static com.simplebank.constant.ErrorMessages.ACCOUNT_ALREADY_EXISTS;
import static com.simplebank.constant.ErrorMessages.ACCOUNT_NOT_FOUND;

@RestController
@RequestMapping(path = "/api/account", produces = "application/json")
public class AccountController {

    @Autowired
    private AccountRepository repository;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(code = HttpStatus.CREATED)
    @ResponseBody
    public Account post(@RequestBody Account request) {
        throwExceptionIfAccountExists(new AccountId(request.getAccountCode(), request.getBankCode()));
        return repository.save(request);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseStatus(code = HttpStatus.OK)
    @ResponseBody
    public Account put(@RequestBody Account request) {
        throwExceptionIfAccountDoesntExist(new AccountId(request.getAccountCode(), request.getBankCode()));
        return repository.save(request);
    }

    @RequestMapping(path = "/{bankCode}/{accountCode}", method = RequestMethod.GET)
    @ResponseStatus(code = HttpStatus.OK)
    @ResponseBody
    public Account get(@PathVariable("bankCode") Long bankCode,
                       @PathVariable("accountCode") Long accountCode) {
        AccountId accountId = new AccountId(accountCode, bankCode);
        throwExceptionIfAccountDoesntExist(accountId);
        return repository.findById(accountId).get();
    }

    @RequestMapping(path = "/{bankCode}/{accountCode}", method = RequestMethod.DELETE)
    @ResponseStatus(code = HttpStatus.OK)
    public void delete(@PathVariable("bankCode") Long bankCode,
                       @PathVariable("accountCode") Long accountCode) {
        AccountId accountId = new AccountId(accountCode, bankCode);
        throwExceptionIfAccountDoesntExist(accountId);
        repository.deleteById(accountId);
    }

    private void throwExceptionIfAccountDoesntExist(AccountId accountId) {
        if(!repository.existsById(accountId))
            throw new NoSuchElementException(String.format(ACCOUNT_NOT_FOUND, accountId.getBankCode(),
                    accountId.getAccountCode()));
    }

    private void throwExceptionIfAccountExists(AccountId accountId) {
        if(repository.existsById(accountId))
            throw new ElementAlreadyExistException(String.format(ACCOUNT_ALREADY_EXISTS, accountId.getBankCode(),
                    accountId.getAccountCode()));
    }

}
