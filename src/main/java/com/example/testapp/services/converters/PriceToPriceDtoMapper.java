package com.example.testapp.services.converters;

import com.example.testapp.dtos.PriceDto;
import com.example.testapp.model.Price;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for converting {@link Price} entities to {@link PriceDto} data transfer objects.
 * This class provides methods to map the entity fields to the corresponding DTO fields,
 * ensuring that the necessary transformations are performed.
 */
@Service
public class PriceToPriceDtoMapper {

    /**
     * Converts a {@link Price} entity to a {@link PriceDto}.
     *
     * @param price the {@link Price} entity to be converted
     * @return a {@link PriceDto} containing the mapped fields from the {@link Price} entity
     */
    public static PriceDto toDto(Price price) {
        return PriceDto.builder()
                .brandId(price.getBrandId())
                .productId(price.getProductId())
                .priceList(price.getPriceList())
                .startDate(price.getStartDate())
                .endDate(price.getEndDate())
                .finalPrice(getFinalPrice(price))
                .build();
    }

    /**
     * Constructs the final price string by concatenating the price value with the currency symbol.
     *
     * @param price the {@link Price} entity containing the price value and currency information
     * @return a string representing the final price including the currency symbol
     */
    private static String getFinalPrice(Price price) {
        return price.getPrice() + price.getCurrency().getSymbol();
    }

}
