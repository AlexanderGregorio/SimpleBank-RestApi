package com.simplebank.test.factory;

import com.simplebank.model.Account;
import com.simplebank.model.Doc;
import com.simplebank.model.User;
import com.simplebank.test.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class EntityFactory {

    @Autowired
    private RandomUtils randomUtils;

    public <T> T getRandomObject(Class<T> elementType) {
        if(Doc.class.equals(elementType))
            return (T) new Doc(randomUtils.nextName(), randomUtils.nextName(),
                    randomUtils.nextBigDecimal(2), "USD");

        if(User.class.equals(elementType))
            return (T) new User(randomUtils.nextNumericString(), randomUtils.nextName());

        if(Account.class.equals(elementType))
            return (T) new Account(randomUtils.nextLong(), getRandomObject(User.class), BigDecimal.ZERO, "PWD");

        throw new IllegalArgumentException();
    }
}
