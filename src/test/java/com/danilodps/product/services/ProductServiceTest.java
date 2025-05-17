package com.danilodps.product.services;

import com.danilodps.product.dtos.ProductDTO;
import com.danilodps.product.entities.Product;
import com.danilodps.product.exceptions.InvalidNameException;
import com.danilodps.product.exceptions.InvalidPriceException;
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

import static org.junit.jupiter.api.Assertions.*;
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
        productEntity.setIdProduct(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));

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

    @Test
    @DisplayName("Cria produto com nome vazio - Deve lançar InvalidNameException")
    void createWithEmptyNameThrowsInvalidNameException() {
        // Arrange
        ProductDTO invalidDTO = ProductDTO.builder()
                .productName("") // Nome vazio!
                .price(BigDecimal.TEN)
                .build();

        // Act & Assert
        assertThrows(InvalidNameException.class, () -> productService.create(invalidDTO));
        verify(productRepository, never()).save(any()); // Garante que o save não foi chamado
    }

    @Test
    @DisplayName("Cria produto com preço nulo - Deve lançar InvalidPriceException")
    void create_WithNullPrice_ThrowsInvalidPriceException() {
        // Arrange
        ProductDTO invalidDTO = ProductDTO.builder()
                .productName("Mouse")
                .price(null) // Preço nulo!
                .build();

        // Act & Assert
        assertThrows(InvalidPriceException.class, () -> productService.create(invalidDTO));
        verify(productRepository, never()).save(any());
    }

    @Test
    @DisplayName("Cria produto com preço zero - Deve lançar InvalidPriceException")
    void create_WithZeroPrice_ThrowsInvalidPriceException() {
        // Arrange
        ProductDTO invalidDTO = ProductDTO.builder()
                .productName("Teclado")
                .price(BigDecimal.ZERO) // Preço zero!
                .build();

        // Act & Assert
        assertThrows(InvalidPriceException.class, () -> productService.create(invalidDTO));
        verify(productRepository, never()).save(any());
    }

    @Test
    @DisplayName("Cria produto com preço negativo - Deve lançar InvalidPriceException")
    void create_WithNegativePrice_ThrowsInvalidPriceException() {
        // Arrange: Cria um ProductDTO com preço negativo usando o Builder
        ProductDTO invalidDTO = ProductDTO.builder()
                .productName("Fone de Ouvido")
                .price(new BigDecimal("-100.00")) // Preço negativo!
                .build();

        // Act & Assert: Verifica se a exceção é lançada
        assertThrows(InvalidPriceException.class, () -> productService.create(invalidDTO));

        // Verifica se o repositório NÃO foi chamado
        verify(productRepository, never()).save(any());
    }
}
