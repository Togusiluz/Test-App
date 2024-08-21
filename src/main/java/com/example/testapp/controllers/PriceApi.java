package com.example.testapp.controllers;

import com.example.testapp.constants.InditexConstants;
import com.example.testapp.dtos.PriceDto;
import com.example.testapp.exceptions.ErrorResponse;
import com.example.testapp.model.Price;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Price API interface that defines endpoints for managing and retrieving price information.
 * It includes a temporary operation for saving a list of prices and another one for retrieving
 * a price based on brand ID, product ID, and date.
 *
 * <p>This API is annotated with OpenAPI annotations for documentation and is validated using
 * Spring's validation framework.</p>
 *
 * @author Francisco Robles
 * @version 0.1.0-SNAPSHOT
 */
@RequestMapping(value = InditexConstants.PRICES_URL)
@RestController
@Validated
@Tag(name = "Price API", description = "API in charge of prices operations")
public interface PriceApi {

    /**
     * @param prices a list of {@link Price} objects to be saved.
     * @return a {@link ResponseEntity} with a status of 201 (Created) if the operation is successful.
     * @deprecated Saves a list of prices into the system.
     *
     * <p><b>Deprecated:</b> This method is provided for demo purposes only and will be deprecated
     * in future versions. It allows clients to save a list of prices, which should be provided
     * in the request body as a JSON array of {@link Price} objects.</p>
     */
    @Deprecated(since = "0.1.0-SNAPSHOT")
    @Operation(
            summary = "Saves a list of prices",
            description =
                    "This method is provided for demo purposes only and will be deprecated. It saves a list of prices " +
                            "into the system. The list should be provided into the body in a JSON with the Price object" +
                            "format",
            method = "POST",
            deprecated = true)
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "CREATED"),
                    @ApiResponse(
                            responseCode = "400",
                            description = "BAD REQUEST",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ErrorResponse.class))
                            }),
                    @ApiResponse(
                            responseCode = "500",
                            description = "INTERNAL SERVER ERROR",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ErrorResponse.class))
                            })
            })
    @PostMapping
    ResponseEntity<Void> createPrices(@RequestBody final List<Price> prices);

    /**
     * Retrieves the price for a given brand ID, product ID, and date.
     *
     * <p>This method fetches the price that applies to a specific brand and product on a given date.
     * All parameters (brand ID, product ID, and date) are required and must be valid.</p>
     *
     * @param date      the date and time for which the price is being requested, in the format 'YYYY/MM/DDTHH:MM:SS'.
     * @param productId the ID of the product for which the price is being requested. Must be a positive number.
     * @param brandId   the ID of the brand for which the price is being requested. Must be a positive number.
     * @return a {@link ResponseEntity} containing the {@link PriceDto} if the price is found, or an appropriate
     * error response if not.
     */
    @Operation(
            summary = "Retrieves a price for a given brand ID, product ID, and date.",
            description =
                    "This method retrieves a price for a given brand ID, product ID, and date into the system. " +
                            "All parameters are required. Date should be in ISO 8601 format (YYYY-MM-DDTHH:MM:SS).",
            method = "POST")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = PriceDto.class))
                            }),
                    @ApiResponse(
                            responseCode = "400",
                            description = "BAD REQUEST",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ErrorResponse.class))
                            }),
                    @ApiResponse(
                            responseCode = "404",
                            description = "NOT FOUND",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ErrorResponse.class))
                            }),
                    @ApiResponse(
                            responseCode = "500",
                            description = "INTERNAL SERVER ERROR",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ErrorResponse.class))
                            })
            })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PriceDto> getPriceByDate(@RequestParam(value = "date") final @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date,
                                            @RequestParam(value = "productId") @Positive final Long productId,
                                            @RequestParam(value = "brandId") @Positive final Long brandId);
}

