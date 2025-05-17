package com.danilodps.product.interfaces;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.danilodps.product.dtos.ProductDTO;
import com.danilodps.product.entities.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {
	
    Product toEntity(ProductDTO productDTO);
    
    ProductDTO toDTO(Product product);
    
    void updateEntityFromDTO(ProductDTO productDTO, @MappingTarget Product product);
}