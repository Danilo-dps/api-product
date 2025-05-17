package com.danilodps.product.dtos;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public class ProductDTO{
	
    private UUID idProduct;
    
    @NotBlank(message = "Nome do produto é obrigatório")
    @Size(min = 3, max = 100)
	private String productName;
    
    @PositiveOrZero(message = "Preço não pode ser negativo")
	private BigDecimal price;
	
	public ProductDTO() {
	}

	public ProductDTO(UUID idProduct, String productName, BigDecimal price) {
		this.idProduct = idProduct;
		this.productName = productName;
		this.price = price;
	}

    public UUID getIdProduct() {
		return idProduct;
	}

	public void setIdProduct(UUID idProduct) {
		this.idProduct = idProduct;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idProduct);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductDTO other = (ProductDTO) obj;
		return Objects.equals(idProduct, other.idProduct);
	}

	@Override
	public String toString() {
		return "Produto "
				+ "Id do produto: " + getIdProduct() 
				+ ", Nome do produto: " + getProductName() 
				+ ", Preço: "+ getPrice();
		
	}
}
