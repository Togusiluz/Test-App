package com.example.testapp.exceptions;

import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

/**
 * GlobalExceptionHandler is a Spring {@code @ControllerAdvice} class responsible for handling exceptions thrown
 * across the application. It provides centralized exception handling and customizes the HTTP responses for different
 * types of exceptions.
 * <p>
 * This class contains handlers for various exceptions, including validation errors, resource not found errors,
 * data access issues, and generic exceptions. Each handler method generates an appropriate HTTP response with
 * status codes and error messages.
 * </p>
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    public static final String ERROR_MESSAGE_DATA_ACCESS_RESOURCE_FAILURE_EXCEPTION = "Currently our database is experiencing difficulties. Please try again later";

    /**
     * Handles {@link ConstraintViolationException} which occurs during validation failures.
     *
     * @param ex      the thrown {@code ConstraintViolationException}
     * @param request the {@code WebRequest} providing request details
     * @return a {@code ResponseEntity} containing a map with error details and HTTP status code 400 (Bad Request)
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(getErrorResponse(request, ex.getMessage(), httpStatus), httpStatus);
    }

    /**
     * Handles {@link MethodArgumentTypeMismatchException} which occurs when a method argument type mismatch happens.
     *
     * @param ex      the thrown {@code MethodArgumentTypeMismatchException}
     * @param request the {@code WebRequest} providing request details
     * @return a {@code ResponseEntity} containing a map with error details and HTTP status code 400 (Bad Request)
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(getErrorResponse(request, ex.getMessage(), httpStatus), httpStatus);
    }


    /**
     * Handles exceptions of type {@link org.springframework.web.bind.MissingServletRequestParameterException} that
     * occur when a required request parameter is missing from the HTTP request.
     *
     * <p>This method is invoked when a controller method is called without a required request parameter.
     * It constructs an appropriate error response with an HTTP status code of 400 (Bad Request).
     *
     * @param ex      the exception thrown when the request parameter is missing.
     * @param request the current {@link org.springframework.web.context.request.WebRequest} in which the exception was raised.
     * @return a {@link org.springframework.http.ResponseEntity} containing the HTTP status, error message,
     * and additional context such as the request path and timestamp.
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(getErrorResponse(request, ex.getMessage(), httpStatus), httpStatus);
    }


    /**
     * Handles {@link NoSuchElementException} which occurs when a requested resource is not found.
     *
     * @param ex      the thrown {@code NoSuchElementException}
     * @param request the {@code WebRequest} providing request details
     * @return a {@code ResponseEntity} containing a map with error details and HTTP status code 404 (Not Found)
     */
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(NoSuchElementException ex, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(getErrorResponse(request, ex.getMessage(), httpStatus), httpStatus);
    }

    /**
     * Handles {@link DataAccessResourceFailureException} which indicates a failure in accessing a data resource.
     *
     * @param ex      the thrown {@code DataAccessResourceFailureException}
     * @param request the {@code WebRequest} providing request details
     * @return a {@code ResponseEntity} containing a map with error details and HTTP status code 500 (Internal Server Error)
     */
    @ExceptionHandler(DataAccessResourceFailureException.class)
    public ResponseEntity<ErrorResponse> handleDataAccessResourceFailureException(DataAccessResourceFailureException ex, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(getErrorResponse(request, ERROR_MESSAGE_DATA_ACCESS_RESOURCE_FAILURE_EXCEPTION, httpStatus), httpStatus);
    }

    /**
     * Handles generic {@link Exception} to catch any unexpected errors that occur during request processing.
     *
     * @param ex      the thrown {@code Exception}
     * @param request the {@code WebRequest} providing request details
     * @return a {@code ResponseEntity} containing a map with error details and HTTP status code 500 (Internal Server Error)
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(getErrorResponse(request, ex.getMessage(), httpStatus), httpStatus);
    }


    /**
     * Constructs the error response body for exceptions.
     *
     * @param request    the {@code WebRequest} providing request details
     * @param message    the error message to include in the response
     * @param httpStatus the HTTP status code to set in the response
     * @return a {@code Map} containing error details such as timestamp, status, error, message, and path
     */
    private static ErrorResponse getErrorResponse(WebRequest request, String message, HttpStatus httpStatus) {
        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(httpStatus.value())
                .message(message)
                .path(request.getDescription(false).substring(4))
                .build();
    }
}
