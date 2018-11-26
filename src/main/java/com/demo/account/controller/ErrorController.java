package com.demo.account.controller;


import com.demo.account.controller.resource.ErrorResource;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Error controller for populating any exception through end point.
 */
@ControllerAdvice
public class ErrorController {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public HttpEntity<ErrorResource> handleException(Exception ex) {
        return getResponse(ex);
    }

    private HttpEntity<ErrorResource> getResponse(Exception ex) {
        HttpStatus httpStatus = getHttpStatus(ex);
        return new ResponseEntity<>(new ErrorResource(ex.getMessage(), httpStatus), httpStatus);
    }

    private HttpStatus getHttpStatus(Exception ex) {
        ResponseStatus status = AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class);
        return status == null ? HttpStatus.INTERNAL_SERVER_ERROR : status.value();
    }
}
