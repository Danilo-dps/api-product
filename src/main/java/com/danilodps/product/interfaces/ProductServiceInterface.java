package com.danilodps.product.interfaces;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.danilodps.product.dtos.ProductDTO;

public interface ProductServiceInterface {
    ProductDTO create(ProductDTO productDTO);
    ProductDTO getById(UUID id);
    List<ProductDTO> findAll();
    ProductDTO update(UUID id, ProductDTO productDTO);
    ProductDTO partiallyUpdate(UUID id, Map<String, Object> field);
    boolean checkExistence(UUID id);
    void delete(UUID id);
}