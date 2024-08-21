package com.example.testapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

@Document("prices")
@Builder(toBuilder = true)
@Getter
@Setter
public class Price {

    @Id
    @JsonIgnore
    @Schema(description = "Document identifier", example = "66c26f01f1fc951335aa8d2d")
    private String _id;

    @JsonProperty("brand_id")
    @Schema(description = "Brand identifier", example = "1")
    private Long brandId;

    @JsonProperty("start_date")
    @Schema(description = "Start date time for the price to be effective", example = "2020-06-15T16:00:00")
    private LocalDateTime startDate;

    @JsonProperty("end_date")
    @Schema(description = "Final date time for the price to be effective", example = "2020-06-15T16:00:00")
    private LocalDateTime endDate;

    @JsonProperty("price_list")
    @Schema(description = "Price list identifier", example = "1")
    private Integer priceList;

    @Schema(description = "Product identifier", example = "35465")
    @JsonProperty("product_id")
    private Long productId;

    @Schema(description = "Price application disambiguator. If two prices overlap in a date range, the one with higher priority (higher numerical value) is applied.", example = "0")
    private Integer priority;

    @Schema(description = "Retail price", example = "35.95")
    private BigDecimal price;

    @JsonProperty("curr")
    @Schema(description = "Retail currency ISO identifier", example = "EUR")
    private Currency currency;

}
