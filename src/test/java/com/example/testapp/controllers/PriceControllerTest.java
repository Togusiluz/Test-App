package com.example.testapp.controllers;

import com.example.testapp.constants.InditexConstants;
import com.example.testapp.exceptions.GlobalExceptionHandler;
import com.example.testapp.mocks.PriceDtoMock;
import com.example.testapp.services.PricesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class PriceControllerTest {

    private static final String BRAND_ID = "brandId";
    private static final String BRAND_ID_VALUE = "1";
    private static final String PRODUCT_ID = "productId";
    private static final String PRODUCT_ID_VALUE = "35643";
    private static final String DATE = "date";
    private static final String DATE_VALUE = LocalDateTime.now().toLocalDate().atStartOfDay().toString();

    @Mock
    private PricesService pricesService;
    private MockMvc mockMvc;
    private ObjectWriter ow;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(PriceController.builder()
                        .pricesService(pricesService)
                        .build())
                .setControllerAdvice(new GlobalExceptionHandler())
                .defaultResponseCharacterEncoding(StandardCharsets.UTF_8)
                .build();

        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        ow = mapper.writer();
    }

    @Test
    void whenGetPriceByDateBrandIdAndProductId_shouldReturnOK() throws Exception {
        when(pricesService.getPriceByBrandIdAndProductIdAndDate(any(LocalDateTime.class), anyLong(), anyLong()))
                .thenReturn(PriceDtoMock.getPriceDto());

        mockMvc.perform(get(InditexConstants.PRICES_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param(BRAND_ID, BRAND_ID_VALUE)
                        .param(PRODUCT_ID, PRODUCT_ID_VALUE)
                        .param(DATE, DATE_VALUE))
                .andExpect(status().isOk());

        verify(pricesService).getPriceByBrandIdAndProductIdAndDate(any(LocalDateTime.class), anyLong(), anyLong());
    }

    @Test
    void whenGetPriceByDateWithValidParams_shouldReturnPriceDto() throws Exception {
        when(pricesService.getPriceByBrandIdAndProductIdAndDate(any(LocalDateTime.class), anyLong(), anyLong()))
                .thenReturn(PriceDtoMock.getPriceDto());

        mockMvc.perform(get(InditexConstants.PRICES_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param(BRAND_ID, BRAND_ID_VALUE)
                        .param(PRODUCT_ID, PRODUCT_ID_VALUE)
                        .param(DATE, DATE_VALUE))
                .andExpect(content().json(ow.writeValueAsString(PriceDtoMock.getPriceDto())));

        verify(pricesService).getPriceByBrandIdAndProductIdAndDate(any(LocalDateTime.class), anyLong(), anyLong());
    }

}
