package com.motoclub.motoclub_service.domain.enums;

public enum DiaSemana {
    DOMINGO("Domingo"),
    SEGUNDA_FEIRA("Segunda-feira"),
    TERCA_FEIRA("Terça-feira"),
    QUARTA_FEIRA("Quarta-feira"),
    QUINTA_FEIRA("Quinta-feira"),
    SEXTA_FEIRA("Sexta-feira"),
    SABADO("Sábado");

    private final String formatted;

    DiaSemana(String formatted) {
        this.formatted = formatted;
    }
    public String toFormattedString(){
        return formatted;
    }
}
