package com.simplebank.api;

import com.simplebank.model.Doc;
import com.simplebank.repository.DocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

import static com.simplebank.constant.ErrorMessages.DOC_NOT_FOUND;

@RestController
@RequestMapping(path = "/api/doc", produces = "application/json")
public class DocController {

    @Autowired
    private DocRepository repository;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(code = HttpStatus.CREATED)
    @ResponseBody
    public Doc post(@RequestBody Doc request) {
        return repository.save(request);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(code = HttpStatus.OK)
    @ResponseBody
    public Doc get(@PathVariable("id") Long id) {
        throwExceptionIfDocDoesntExist(id);
        return repository.findById(id).get();
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseStatus(code = HttpStatus.OK)
    @ResponseBody
    public Doc put(@RequestBody Doc request) {
        throwExceptionIfDocDoesntExist(request.getId());
        return repository.save(request);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(code = HttpStatus.OK)
    public void delete(@RequestParam("id") Long id) {
        throwExceptionIfDocDoesntExist(id);
        repository.deleteById(id);
    }

    private void throwExceptionIfDocDoesntExist(Long id) {
        if(!repository.existsById(id))
            throw new NoSuchElementException(String.format(DOC_NOT_FOUND, id));
    }

}
