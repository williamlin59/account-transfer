package com.demo.account.controller;

import com.demo.account.controller.resource.ErrorResource;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

class ErrorControllerTest {
    private final ErrorController errorController = new ErrorController();

    @Test
    void handleException() {
        HttpEntity<ErrorResource> errorResourceHttpEntity = errorController.handleException(new RuntimeException("Error"));
        assertThat(errorResourceHttpEntity.getBody().getMessage()).isEqualTo("Error");
        assertThat(errorResourceHttpEntity.getBody().getHttpStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}