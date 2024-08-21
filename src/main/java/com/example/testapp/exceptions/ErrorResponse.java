package com.example.testapp.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class ErrorResponse {

    @Schema(description = "Error timestamp", example = "2020-06-15T16:00:00")
    private LocalDateTime timestamp;

    @Schema(description = "HTTP status code", example = "404")
    private int status;

    @Schema(description = "Message describing the error", example = "Resource not found")
    private String message;

    @Schema(description = "Path of the resource", example = "/resource")
    private String path;

}
