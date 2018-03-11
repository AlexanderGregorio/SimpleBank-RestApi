package com.simplebank.test.factory;

import com.simplebank.model.Doc;
import com.simplebank.model.User;
import com.simplebank.test.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EntityFactory {

    @Autowired
    private RandomUtils randomUtils;

    public <T> T getRandomObject(Class<T> clazz) {
        if(Doc.class.equals(clazz))
            return (T) new Doc(randomUtils.nextName(), randomUtils.nextName(),
                    randomUtils.nextBigDecimal(2), "USD");

        if(User.class.equals(clazz))
            return (T) new User(randomUtils.nextDocumentCode(), randomUtils.nextName());

        throw new IllegalArgumentException();
    }
}
