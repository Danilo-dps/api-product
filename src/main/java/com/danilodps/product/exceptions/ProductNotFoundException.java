package com.danilodps.product.exceptions;

import java.util.UUID;

public class ProductNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ProductNotFoundException(UUID id) {
        super("Produto com ID " + id + " não encontrado.");
    }
}
