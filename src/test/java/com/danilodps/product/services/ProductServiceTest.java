package com.danilodps.product.services;

import com.danilodps.product.dtos.ProductDTO;
import com.danilodps.product.entities.Product;
import com.danilodps.product.interfaces.ProductMapper;
import com.danilodps.product.repositories.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    private ProductDTO createValidProductDTO() {
        return ProductDTO.builder()
                .productName("Notebook Dell")
                .price(BigDecimal.valueOf(4500.00))
                .build();
    }

    @Test
    @DisplayName("Cria produto com dados válidos - Deve retornar ProductDTO salvo")
    void createWithValidDataReturnsSavedProductDTO() {
        // Arrange
        ProductDTO inputDTO = createValidProductDTO();

        Product productEntity = new Product();
        productEntity.setIdProduct(UUID.fromString("123e4567-e89b-12d3-a456-426614174000")); // Simula ID gerado

        ProductDTO expectedDTO = ProductDTO.builder()
                .idProduct(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"))
                .build();

        when(productMapper.toEntity(inputDTO)).thenReturn(productEntity);
        when(productRepository.save(productEntity)).thenReturn(productEntity);
        when(productMapper.toDTO(productEntity)).thenReturn(expectedDTO);

        // Act
        ProductDTO result = productService.create(inputDTO);

        // Assert
        assertNotNull(result);
        assertEquals(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"), result.getIdProduct());
        verify(productRepository, times(1)).save(productEntity);
    }
}
