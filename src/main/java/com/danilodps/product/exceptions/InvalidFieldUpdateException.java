package com.danilodps.product.exceptions;

public class InvalidFieldUpdateException extends RuntimeException {
    private static final long serialVersionUID = 1L;

	public InvalidFieldUpdateException(String field) {
        super("Campo '" + field + "' não é válido para atualização.");
    }
}