package com.pedro.health.infra.exceptions.global;

import org.springframework.http.HttpStatus;

public record ErrorMessage(HttpStatus status, String message) {
}
