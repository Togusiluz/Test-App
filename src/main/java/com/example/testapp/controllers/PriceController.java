package com.example.testapp.controllers;

import com.example.testapp.dtos.PriceDto;
import com.example.testapp.model.Price;
import com.example.testapp.services.PricesService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@Builder
@AllArgsConstructor
public class PriceController implements PriceApi {

    private final PricesService pricesService;

    /**
     * {@inheritDoc }
     */
    @Override
    public ResponseEntity<Void> createPrices(List<Price> prices) {
        pricesService.addPrices(prices);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ResponseEntity<PriceDto> getPriceByDate(LocalDateTime date, Long productId, Long brandId) {
        return new ResponseEntity<>(pricesService.getPriceByBrandIdAndProductIdAndDate(date, brandId, productId), HttpStatus.OK);
    }


}
