package com.danilodps.product.exceptions;

import java.math.BigDecimal;

public class InvalidPriceException extends RuntimeException {
    private static final long serialVersionUID = 1L;

	public InvalidPriceException(BigDecimal price) {
        super("Preço " + price + " é inválido. Deve ser maior que zero.");
    }
}