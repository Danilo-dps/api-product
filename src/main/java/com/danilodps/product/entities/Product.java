package com.danilodps.product.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tb_produtos")
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "UUID")
    private UUID idProduct;

    @NotBlank(message = "Nome do produto é obrigatório")
    @Size(min = 3, max = 100, message = "Nome deve ter 3-100 caracteres")
    @Column(nullable = false, length = 100)
    private String productName;

    @PositiveOrZero(message = "Preço não pode ser negativo")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

	public Product() {
	}

	public Product(UUID idProduct, String productName, BigDecimal price) {
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
		Product other = (Product) obj;
		return Objects.equals(idProduct, other.idProduct);
	}

	@Override
	public String toString() {
		return "Produto " 
				+ "Id do produto: " + getIdProduct() 
				+ ", Nome do produto: " + getProductName() 
			    + ", Preço: " + getPrice();
	}
}
