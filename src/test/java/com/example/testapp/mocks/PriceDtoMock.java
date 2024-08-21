package com.example.testapp.mocks;

import com.example.testapp.dtos.PriceDto;

import java.time.LocalDateTime;

public class PriceDtoMock {

    private static final long BRAND_ID = 1L;
    private static final long PRODUCT_ID = 35643L;
    private static final LocalDateTime START_DATE = LocalDateTime.now().minusMonths(1).toLocalDate().atStartOfDay();
    private static final LocalDateTime END_DATE = LocalDateTime.now().plusMonths(1).toLocalDate().atStartOfDay();
    private static final int PRICE_LIST = 1;
    private static final String FINAL_PRICE = "35.99€";
    private static final String FINAL_PRICE_2 = "15.99€";

    public static PriceDto getPriceDto() {
        return PriceDto.builder()
                .brandId(BRAND_ID)
                .productId(PRODUCT_ID)
                .startDate(START_DATE)
                .endDate(END_DATE)
                .priceList(PRICE_LIST)
                .finalPrice(FINAL_PRICE)
                .build();
    }

    public static PriceDto getPriceDtoWithLowerPriority() {
        return getPriceDto().toBuilder()
                .finalPrice(FINAL_PRICE_2)
                .build();
    }
}
