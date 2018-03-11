package com.simplebank.test.factory;

import com.simplebank.model.Doc;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DocFactory {

    public Doc getRandomDoc(){
        return new Doc("Mary", "John", new BigDecimal("100.00"), "USD");
    }
}
