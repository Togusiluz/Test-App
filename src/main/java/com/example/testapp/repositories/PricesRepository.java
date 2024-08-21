package com.example.testapp.repositories;

import com.example.testapp.model.Price;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for managing {@link Price} entities in a MongoDB database.
 * This interface provides methods to perform CRUD operations and custom queries on price data.
 */
public interface PricesRepository extends CrudRepository<Price, Long> {

    /**
     * Finds a list of {@link Price} entities that match the given brand ID, product ID, and date.
     * The query filters by the date being within the start and end dates of the price, and sorts results by priority.
     *
     * @param date      the date to filter prices by, must be within the start and end dates of the price
     * @param brandId   the ID of the brand
     * @param productId the ID of the product
     * @return a list of {@link Price} entities that match the given criteria, sorted by priority
     */
    @Query(value = "{ 'brandId' : ?0, 'productId' : ?1, 'startDate' : { $lte: ?2 }, 'endDate' : { $gte: ?2 } }", sort = "{ 'priority': 1 }")
    List<Price> findByBrandIdAndProductIdAndDate(long brandId, long productId, LocalDateTime date);


}
