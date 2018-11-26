package com.demo.account.controller.resource;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * ErrorResource contains data for Error controller to populate
 */
@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResource implements Serializable {
    private static final long serialVersionUID = 2602802155506340076L;
    private String message;
    private HttpStatus httpStatus;
}
