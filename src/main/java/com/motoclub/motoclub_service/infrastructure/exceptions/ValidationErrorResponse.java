package com.motoclub.motoclub_service.infrastructure.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ValidationErrorResponse {
    private final int status;
    private final String message;
    private final List<FieldErrorResponse> errors;
}
