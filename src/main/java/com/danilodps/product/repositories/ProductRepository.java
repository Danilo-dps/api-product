package com.danilodps.product.repositories;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

import com.danilodps.product.entities.Product;

public interface ProductRepository extends JpaRepository<Product, UUID> {

}
