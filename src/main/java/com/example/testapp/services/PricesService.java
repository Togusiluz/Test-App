package com.example.testapp.services;

import com.example.testapp.dtos.PriceDto;
import com.example.testapp.model.Price;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service interface for handling operations related to prices.
 * This interface defines methods to retrieve pricing information based on various criteria
 * and to add new prices to the system.
 */
public interface PricesService {

    /**
     * Retrieves a {@link PriceDto} for a given brand ID, product ID, and date.
     *
     * @param date      the date and time for which the price is being queried
     * @param brandId   the ID of the brand
     * @param productId the ID of the product
     * @return a {@link PriceDto} containing the pricing information that matches the criteria
     * @throws java.util.NoSuchElementException if no matching price is found
     */
    PriceDto getPriceByBrandIdAndProductIdAndDate(LocalDateTime date, Long brandId, Long productId);

    /**
     * Adds a list of {@link Price} entities to the system.
     *
     * @param prices a list of {@link Price} entities to be added
     */
    void addPrices(List<Price> prices);
}
