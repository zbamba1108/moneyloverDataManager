package dev.boog.moneyloverdatamanager.exceptions;

import org.springframework.validation.FieldError;

import java.util.List;

public record ValidationFields(String field, String message) {
}
