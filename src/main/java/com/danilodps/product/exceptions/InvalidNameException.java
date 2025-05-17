package com.danilodps.product.exceptions;

public class InvalidNameException extends RuntimeException {
    private static final long serialVersionUID = 1L;

	public InvalidNameException(String name) {
        super("Nome não pode ser vazio");
    }
}