package com.example.testapp.services;

import com.example.testapp.dtos.PriceDto;
import com.example.testapp.model.Price;
import com.example.testapp.repositories.PricesRepository;
import com.example.testapp.services.converters.PriceToPriceDtoMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import static com.example.testapp.constants.InditexConstants.ERROR_MESSAGE_NO_SUCH_ELEMENT_EXCEPTION;

/**
 * Implementation of the {@link PricesService} interface for handling price-related operations.
 * This service provides methods to retrieve pricing information and add new prices to the system.
 */
@Service
public class PricesServiceImpl implements PricesService {

    private final PricesRepository pricesRepository;

    /**
     * {@inheritDoc }
     */
    public PricesServiceImpl(PricesRepository pricesRepository) {
        this.pricesRepository = pricesRepository;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public PriceDto getPriceByBrandIdAndProductIdAndDate(LocalDateTime date, Long brandId, Long productId) {
        List<Price> prices = pricesRepository.findByBrandIdAndProductIdAndDate(brandId, productId, date);
        return prices.stream()
                .findFirst()
                .map(PriceToPriceDtoMapper::toDto)
                .orElseThrow(() -> new NoSuchElementException(String.format(ERROR_MESSAGE_NO_SUCH_ELEMENT_EXCEPTION, date, brandId, productId)));
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void addPrices(List<Price> prices) {
        pricesRepository.saveAll(prices);
    }
}
