package com.simplebank.test.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class Parser {

    public <T> T asObject(String json, Class<T> clazz) {
        try {
            ObjectMapper mapper = new ObjectMapper()
                    .registerModule(new ParameterNamesModule())
                    .registerModule(new Jdk8Module())
                    .registerModule(new JavaTimeModule());
            return mapper.readValue(json, clazz);
        }
        catch (IOException e) {
            log.error("There was a problem parsing the object.", e);
        }

        return null;
    }

    public String asString(Object object) {
        try {
            ObjectMapper mapper = new ObjectMapper()
                    .registerModule(new ParameterNamesModule())
                    .registerModule(new Jdk8Module())
                    .registerModule(new JavaTimeModule());
            return mapper.writeValueAsString(object);
        }
        catch (JsonProcessingException e) {
            log.error("There was a problem parsing the object.", e);
        }
        return null;
    }
}
