package com.example.testapp.services;

import com.example.testapp.dtos.PriceDto;
import com.example.testapp.mocks.PriceDtoMock;
import com.example.testapp.mocks.PriceMock;
import com.example.testapp.model.Price;
import com.example.testapp.repositories.PricesRepository;
import com.example.testapp.services.converters.PriceToPriceDtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class PricesServiceImplTest {

    public static final long BRAND_ID = 1L;
    public static final long PRODUCT_ID = 35455L;
    public static final LocalDateTime DATE = LocalDateTime.now();
    public static final String NO_SUCH_ELEMENT_EXCEPTION_MESSAGE = "No price found for date: null id: 1 and productId: 35455";
    @Mock
    private PricesRepository pricesRepository;


    @InjectMocks
    private PricesServiceImpl pricesService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        pricesService = new PricesServiceImpl(pricesRepository);
    }

    @Test
    void whenFindByBrandIdAndProductIdAndDate_shouldReturnPriceDto() {
        when(pricesRepository.findByBrandIdAndProductIdAndDate(anyLong(), anyLong(), any(LocalDateTime.class))).thenReturn(Collections.singletonList(PriceMock.getPrice()));

        try (MockedStatic<PriceToPriceDtoMapper> priceToPriceDtoMapper = mockStatic(PriceToPriceDtoMapper.class)) {
            priceToPriceDtoMapper
                    .when(() -> PriceToPriceDtoMapper.toDto(any(Price.class)))
                    .thenReturn(PriceDtoMock.getPriceDto());

            PriceDto result = pricesService.getPriceByBrandIdAndProductIdAndDate(DATE, BRAND_ID, PRODUCT_ID);

            verify(pricesRepository).findByBrandIdAndProductIdAndDate(anyLong(), anyLong(), any(LocalDateTime.class));
            priceToPriceDtoMapper.verify(() -> PriceToPriceDtoMapper.toDto(any(Price.class)));
            assertEquals(PriceDtoMock.getPriceDto(), result);
        }
    }

    @Test
    void whenFindByBrandIdAndProductIdAndDateReturnsMultipleItems_shouldReturnPriceDtoWithLowerPriority() {
        when(pricesRepository.findByBrandIdAndProductIdAndDate(anyLong(), anyLong(), any(LocalDateTime.class))).thenReturn(PriceMock.getListWithMultipleItems());

        try (MockedStatic<PriceToPriceDtoMapper> priceToPriceDtoMapper = mockStatic(PriceToPriceDtoMapper.class)) {
            priceToPriceDtoMapper
                    .when(() -> PriceToPriceDtoMapper.toDto(any(Price.class)))
                    .thenReturn(PriceDtoMock.getPriceDtoWithLowerPriority());

            PriceDto result = pricesService.getPriceByBrandIdAndProductIdAndDate(DATE, BRAND_ID, PRODUCT_ID);

            assertEquals(PriceDtoMock.getPriceDtoWithLowerPriority(), result);
            verify(pricesRepository).findByBrandIdAndProductIdAndDate(anyLong(), anyLong(), any(LocalDateTime.class));
        }
    }

    @Test
    void whenFindByBrandIdAndProductIdAndDateReturnsNoItems_shouldThrowNoSuchElementException() {
        when(pricesRepository.findByBrandIdAndProductIdAndDate(anyLong(), anyLong(), nullable(LocalDateTime.class))).thenReturn(Collections.emptyList());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> pricesService.getPriceByBrandIdAndProductIdAndDate(null, BRAND_ID, PRODUCT_ID));

        verify(pricesRepository).findByBrandIdAndProductIdAndDate(anyLong(), anyLong(), nullable(LocalDateTime.class));
        assertEquals(NO_SUCH_ELEMENT_EXCEPTION_MESSAGE, exception.getMessage());
    }

    @Test
    void whenAddPrices_shouldExecutePricesRepositorySaveAll() {
        when(pricesRepository.saveAll(any(List.class))).thenReturn(Collections.emptyList());

        pricesService.addPrices(PriceMock.getListWithMultipleItems());

        verify(pricesRepository).saveAll(any(List.class));
    }


}
