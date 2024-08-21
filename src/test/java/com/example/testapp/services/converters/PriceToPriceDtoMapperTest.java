package com.example.testapp.services.converters;

import com.example.testapp.dtos.PriceDto;
import com.example.testapp.mocks.PriceMock;
import com.example.testapp.model.Price;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PriceToPriceDtoMapperTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void whenPriceToPriceDtoMapperToDtoMapsBrandId_shouldReturnPriceBrandId() {
        Price price = PriceMock.getPrice();
        PriceDto result = PriceToPriceDtoMapper.toDto(price);
        assertEquals(price.getBrandId(), result.getBrandId());
    }

    @Test
    void whenPriceToPriceDtoMapperToDtoMapsProductId_shouldReturnPriceProductId() {
        Price price = PriceMock.getPrice();
        PriceDto result = PriceToPriceDtoMapper.toDto(price);
        assertEquals(price.getProductId(), result.getProductId());
    }

    @Test
    void whenPriceToPriceDtoMapperToDtoMapsPriceList_shouldReturnPricePriceList() {
        Price price = PriceMock.getPrice();
        PriceDto result = PriceToPriceDtoMapper.toDto(price);
        assertEquals(price.getPriceList(), result.getPriceList());
    }

    @Test
    void whenPriceToPriceDtoMapperToDtoMapsStartDate_shouldReturnPriceStartDate() {
        Price price = PriceMock.getPrice();
        PriceDto result = PriceToPriceDtoMapper.toDto(price);
        assertEquals(price.getStartDate(), result.getStartDate());
    }

    @Test
    void whenPriceToPriceDtoMapperToDtoMapsEndDate_shouldReturnPriceEndDate() {
        Price price = PriceMock.getPrice();
        PriceDto result = PriceToPriceDtoMapper.toDto(price);
        assertEquals(price.getEndDate(), result.getEndDate());
    }

    @Test
    void whenPriceToPriceDtoMapperToDtoMapsFinalPrice_shouldReturnPriceFinalPrice() {
        Price price = PriceMock.getPrice();
        PriceDto result = PriceToPriceDtoMapper.toDto(price);
        assertEquals(price.getPrice() + price.getCurrency().getSymbol(), result.getFinalPrice());
    }
}
