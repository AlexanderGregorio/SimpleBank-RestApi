package com.simplebank.api;

import com.simplebank.exception.ElementAlreadyExistException;
import com.simplebank.model.User;
import com.simplebank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

import static com.simplebank.constant.ErrorMessages.USER_ALREADY_EXISTS;
import static com.simplebank.constant.ErrorMessages.USER_NOT_FOUND;

@RestController
@RequestMapping(path = "/api/user", produces = "application/json")
public class UserController {

    @Autowired
    private UserRepository repository;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(code = HttpStatus.CREATED)
    @ResponseBody
    public User post(@RequestBody User request) {
        throwExceptionIfUserExists(request.getDocumentCode());
        return repository.save(request);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseStatus(code = HttpStatus.OK)
    @ResponseBody
    public User put(@RequestBody User request) {
        throwExceptionIfUserDoesntExist(request.getDocumentCode());
        return repository.save(request);
    }

    @RequestMapping(path = "/{documentCode}", method = RequestMethod.GET)
    @ResponseStatus(code = HttpStatus.OK)
    @ResponseBody
    public User get(@PathVariable("documentCode") String documentCode) {
        throwExceptionIfUserDoesntExist(documentCode);
        return repository.findById(documentCode).get();
    }

    @RequestMapping(path = "/{documentCode}", method = RequestMethod.DELETE)
    @ResponseStatus(code = HttpStatus.OK)
    public void delete(@PathVariable("documentCode") String documentCode) {
        throwExceptionIfUserDoesntExist(documentCode);
        repository.deleteById(documentCode);
    }

    private void throwExceptionIfUserDoesntExist(String documentCode) {
        if(!repository.existsById(documentCode))
            throw new NoSuchElementException(String.format(USER_NOT_FOUND, documentCode));
    }

    private void throwExceptionIfUserExists(String documentCode) {
        if(repository.existsById(documentCode))
            throw new ElementAlreadyExistException(String.format(USER_ALREADY_EXISTS, documentCode));
    }

}
