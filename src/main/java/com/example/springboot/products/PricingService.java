package com.example.springboot.products;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@Component
public class PricingService {

    private final NoSQLWrapper noSQLWrapper;
    public static final BigDecimal ONE_HUNDRED = BigDecimal.TEN.multiply(BigDecimal.TEN);
    private static MathContext mc = new MathContext(10, RoundingMode.HALF_EVEN);

    public PricingService(NoSQLWrapper noSQLWrapper) {
        this.noSQLWrapper = noSQLWrapper;
    }

    public ProductController.PriceDTO getPriceForItem(Long tcin) {
        BigDecimal price = noSQLWrapper.retrievePriceByTcin(tcin);
        return price != null
                ? ProductController.PriceDTO.builder()
                    .currency_code("USD")
                    .value(price)
                    .build()
                : null;
    }

    public ProductController.PriceDTO updatePriceForItem(Long tcin, Long priceInUsdCents) {
        BigDecimal price = BigDecimal.valueOf(priceInUsdCents).divide(ONE_HUNDRED, mc);
        boolean updateSucceeded = noSQLWrapper.updatePriceByTcin(tcin, price);
        return updateSucceeded
                ? ProductController.PriceDTO.builder()
                    .currency_code("USD")
                    .value(price)
                    .build()
                : null;
    }

}
