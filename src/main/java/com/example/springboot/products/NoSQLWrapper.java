package com.example.springboot.products;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

@Component
public class NoSQLWrapper {
    private static final BigDecimal ONE_HUNDRED = BigDecimal.TEN.multiply(BigDecimal.TEN);

    private static MathContext mc = new MathContext(10, RoundingMode.HALF_EVEN);

    private static final Map<Long, BigDecimal> tcinToPrice;
    static {
        Map<Long, BigDecimal> map = new HashMap<>();
        map.put(13860428L, BigDecimal.valueOf(123L).divide(ONE_HUNDRED, mc));
        map.put(54456119L, BigDecimal.valueOf(234L).divide(ONE_HUNDRED, mc));
        map.put(13264003L, BigDecimal.valueOf(345L).divide(ONE_HUNDRED, mc));
        map.put(12954218L, BigDecimal.valueOf(456L).divide(ONE_HUNDRED, mc));
        tcinToPrice = map;
    }

    public BigDecimal retrievePriceByTcin(Long tcin) {
        return tcinToPrice.getOrDefault(tcin, null);
    }

    public boolean updatePriceByTcin(Long tcin, BigDecimal price) {
        if(!tcinToPrice.containsKey(tcin)) {
            return false;
        }
        tcinToPrice.put(tcin, price);
        return true;
    }
}
