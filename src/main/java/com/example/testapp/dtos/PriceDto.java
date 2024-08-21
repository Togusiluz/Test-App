package com.example.testapp.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder(toBuilder = true)
@Getter
@Setter
@EqualsAndHashCode
public class PriceDto {

    @Schema(description = "Product identifier", example = "35465")
    private Long productId;

    @Schema(description = "Brand identifier", example = "1")
    private Long brandId;

    @Schema(description = "Price list identifier", example = "1")
    private Integer priceList;

    @Schema(description = "Start date time for the price to be effective", example = "2020-06-15T16:00:00")
    private LocalDateTime startDate;

    @Schema(description = "Final date time for the price to be effective", example = "2020-06-15T16:00:00")
    private LocalDateTime endDate;

    @Schema(description = "Retail price, including the ISO currency symbol", example = "35.95€")
    private String finalPrice;

}
