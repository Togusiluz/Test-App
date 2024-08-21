package com.example.testapp.mocks;

import com.example.testapp.model.Price;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;

public class PriceMock {

    private static final String ID = "66c26f01f1fc951335aa8d2d";
    private static final long BRAND_ID = 1L;
    private static final long PRODUCT_ID = 35643L;
    private static final LocalDateTime START_DATE = LocalDateTime.now().minusMonths(1).toLocalDate().atStartOfDay();
    private static final LocalDateTime END_DATE = LocalDateTime.now().plusMonths(1).toLocalDate().atStartOfDay();
    private static final int PRICE_LIST = 1;
    private static final int LOWER_PRIORITY = 0;
    private static final int PRIORITY = 1;
    private static final BigDecimal PRICE = BigDecimal.valueOf(35.99);
    private static final BigDecimal PRICE_2 = BigDecimal.valueOf(15.99);
    private static final Currency CURRENCY = Currency.getInstance("EUR");

    public static Price getPrice() {
        return Price.builder()
                ._id(ID)
                .brandId(BRAND_ID)
                .productId(PRODUCT_ID)
                .startDate(START_DATE)
                .endDate(END_DATE)
                .priceList(PRICE_LIST)
                .priority(PRIORITY)
                .price(PRICE)
                .currency(CURRENCY)
                .build();
    }

    public static Price getPriceLowerPriority() {
        return getPrice().toBuilder()
                .priority(LOWER_PRIORITY)
                .price(PRICE_2)
                .build();
    }

    public static List<Price> getListWithMultipleItems() {
        return Arrays.asList(getPrice(), getPriceLowerPriority());
    }


}
