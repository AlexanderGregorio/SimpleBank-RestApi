package com.simplebank.test;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class RandomUtils {

    private ThreadLocalRandom random = ThreadLocalRandom.current();

    private String[] names = {"JAMES", "JOHN", "ROBERT", "MICHAEL", "WILLIAM", "MARY", "PATRICIA", "LINDA", "BARBARA", "ELIZABETH"};
    private String[] surnames = {"SMITH", "JOHNSON", "WILLIAMS", "JONES", "BROWN", "DAVIS", "MILLER", "WILSON", "MOORE", "TAYLOR"};

    public String nextName() {
        StringBuilder name = new StringBuilder();
        name.append(names[random.nextInt(names.length)]).append(" ");
        name.append(surnames[random.nextInt(surnames.length)]);
        return name.toString();
    }

    public BigDecimal nextBigDecimal(int decimalPlaces) {
        return nextBigDecimal(Integer.MIN_VALUE, Integer.MAX_VALUE, decimalPlaces);
    }

    public BigDecimal nextBigDecimal(int origin, int bound, int decimalPlaces) {
        StringBuilder numberString = new StringBuilder();

        numberString.append(random.nextInt(origin, bound));
        if(decimalPlaces > 0) {
            numberString.append(".");
            for(int i = 0; i < decimalPlaces; i++){
                numberString.append(random.nextInt(0, 10));
            }
        }

        return new BigDecimal(numberString.toString());
    }

    public String nextNumericString() {
        return String.valueOf(Math.abs(random.nextInt()));
    }

    public Long nextLong() {
        return random.nextLong();
    }
}
