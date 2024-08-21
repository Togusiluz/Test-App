package com.example.testapp.exceptions;

import com.example.testapp.mocks.PriceDtoMock;
import com.example.testapp.services.PricesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.NoSuchElementException;

import static com.example.testapp.constants.InditexConstants.PRICES_URL;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GlobalExceptionHandlerTest {

    private static final String BRAND_ID = "brandId";
    private static final String BRAND_ID_VALUE = "1";
    private static final String BRAND_ID_INVALID = "invalidBrand";
    private static final String BRAND_ID_NEGATIVE = "-1";
    private static final String PRODUCT_ID = "productId";
    private static final String PRODUCT_ID_VALUE = "35643";
    private static final String PRODUCT_ID_INVALID = "invalidProductId";
    private static final String PRODUCT_ID_NEGATIVE = "-1";
    private static final String DATE = "date";
    private static final String DATE_VALUE = LocalDateTime.now().toLocalDate().atStartOfDay().toString();
    private static final String DATE_INVALID = "invalidDate";
    private static final String ERROR_MESSAGE = "Error message";

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PricesService pricesService;
    private ObjectWriter ow;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        ow = mapper.writer();
    }

    @Test
    void whenGetPriceByDateWithNegativeProductId_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(get(PRICES_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param(BRAND_ID, BRAND_ID_VALUE)
                        .param(PRODUCT_ID, BRAND_ID_NEGATIVE)
                        .param(DATE, DATE_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGetPriceByDateWithInvalidProductId_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(get(PRICES_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param(BRAND_ID, BRAND_ID_VALUE)
                        .param(PRODUCT_ID, PRODUCT_ID_INVALID)
                        .param(DATE, DATE_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGetPriceByDateWithNoProductId_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(get(PRICES_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param(BRAND_ID, BRAND_ID_VALUE)
                        .param(DATE, DATE_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGetPriceByDateWithNegativeBrandId_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(get(PRICES_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param(BRAND_ID, PRODUCT_ID_NEGATIVE)
                        .param(PRODUCT_ID, PRODUCT_ID_VALUE)
                        .param(DATE, DATE_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGetPriceByDateWithInvalidBrandId_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(get(PRICES_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param(BRAND_ID, BRAND_ID_INVALID)
                        .param(PRODUCT_ID, PRODUCT_ID_VALUE)
                        .param(DATE, DATE_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGetPriceByDateWithNoBrandId_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(get(PRICES_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param(PRODUCT_ID, PRODUCT_ID_VALUE)
                        .param(DATE, DATE_INVALID))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGetPriceByDateWithInvalidFormatDate_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(get(PRICES_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param(BRAND_ID, BRAND_ID_VALUE)
                        .param(PRODUCT_ID, PRODUCT_ID_VALUE)
                        .param(DATE, DATE_INVALID))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGetPriceByDateWithInvalidDate_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(get(PRICES_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param(BRAND_ID, BRAND_ID_VALUE)
                        .param(PRODUCT_ID, PRODUCT_ID_VALUE)
                        .param(DATE, DATE_INVALID))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGetPriceByDateWithNoDate_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(get(PRICES_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param(BRAND_ID, BRAND_ID_VALUE)
                        .param(PRODUCT_ID, PRODUCT_ID_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenCreatePrices_shouldReturnCreatedStatus() throws Exception {
        mockMvc.perform(post(PRICES_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ow.writeValueAsString(Collections.singletonList(PriceDtoMock.getPriceDto()))))
                .andExpect(status().isCreated());
    }

    @Test
    void whenGetPriceByDateThrowsDataAccessResourceFailureException_shouldReturnInternalServerError() throws Exception {
        doThrow(new DataAccessResourceFailureException(ERROR_MESSAGE))
                .when(pricesService)
                .getPriceByBrandIdAndProductIdAndDate(any(LocalDateTime.class), anyLong(), anyLong());

        mockMvc.perform(get(PRICES_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param(BRAND_ID, BRAND_ID_VALUE)
                        .param(PRODUCT_ID, PRODUCT_ID_VALUE)
                        .param(DATE, DATE_VALUE))
                .andExpect(status().isInternalServerError());

        verify(pricesService).getPriceByBrandIdAndProductIdAndDate(any(LocalDateTime.class), anyLong(), anyLong());
    }

    @Test
    void whenGetPriceByDateThrowsNoSuchElementException_shouldReturnInternalServerError() throws Exception {
        doThrow(new NoSuchElementException(ERROR_MESSAGE))
                .when(pricesService)
                .getPriceByBrandIdAndProductIdAndDate(any(LocalDateTime.class), anyLong(), anyLong());

        mockMvc.perform(get(PRICES_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param(BRAND_ID, BRAND_ID_VALUE)
                        .param(PRODUCT_ID, PRODUCT_ID_VALUE)
                        .param(DATE, DATE_VALUE))
                .andExpect(status().isNotFound());

        verify(pricesService).getPriceByBrandIdAndProductIdAndDate(any(LocalDateTime.class), anyLong(), anyLong());
    }

    @Test
    void whenGetPriceByDateWithValidParamsThrowsException_shouldReturnInternalServerError() throws Exception {
        doThrow(new RuntimeException(ERROR_MESSAGE))
                .when(pricesService)
                .getPriceByBrandIdAndProductIdAndDate(any(LocalDateTime.class), anyLong(), anyLong());

        mockMvc.perform(get(PRICES_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param(BRAND_ID, BRAND_ID_VALUE)
                        .param(PRODUCT_ID, PRODUCT_ID_VALUE)
                        .param(DATE, DATE_VALUE))
                .andExpect(status().isInternalServerError());

        verify(pricesService).getPriceByBrandIdAndProductIdAndDate(any(LocalDateTime.class), anyLong(), anyLong());
    }

}
