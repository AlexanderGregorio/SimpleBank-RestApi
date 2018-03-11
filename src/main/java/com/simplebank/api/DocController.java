package com.simplebank.api;

import com.simplebank.model.Doc;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/com/simplebank/api/doc", produces = "application/json")
public class DocController {

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(code = HttpStatus.CREATED)
    @ResponseBody
    public Doc post(@RequestBody Doc request) {
        return null;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(code = HttpStatus.OK)
    @ResponseBody
    public Doc get(@RequestBody Doc request) {
        return null;
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseStatus(code = HttpStatus.OK)
    @ResponseBody
    public Doc put(@RequestBody Doc request) {
        return null;
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseStatus(code = HttpStatus.OK)
    @ResponseBody
    public Doc delete(@RequestBody Doc request) {
        return null;
    }
}
