package com.motoclub.motoclub_service.infrastructure.exceptions;

import lombok.Getter;

@Getter
public class RecursoNaoEncontradoException extends RuntimeException {

    private final String field;

    public RecursoNaoEncontradoException(String field, String message) {
        super(message);
        this.field = field;
    }
}
