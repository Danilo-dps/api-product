package com.danilodps.product.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.danilodps.product.dtos.ProductDTO;
import com.danilodps.product.entities.Product;
import com.danilodps.product.exceptions.InvalidFieldUpdateException;
import com.danilodps.product.exceptions.InvalidNameException;
import com.danilodps.product.exceptions.InvalidPriceException;
import com.danilodps.product.exceptions.ProductNotFoundException;
import com.danilodps.product.interfaces.ProductMapper;
import com.danilodps.product.interfaces.ProductServiceInterface;
import com.danilodps.product.repositories.ProductRepository;

import jakarta.transaction.Transactional;

@Service
public class ProductService implements ProductServiceInterface {
	private static final Logger logger = Logger.getLogger(ProductService.class.getName());


    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }
    
    @Override
    @Transactional
    public ProductDTO getById(UUID id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException(id));
        return productMapper.toDTO(product);
    }

    @Override
    @Transactional
    public List<ProductDTO> findAll() {
        return productRepository.findAll().stream()
            .map(productMapper::toDTO)
            .toList();
    }
    
    @Override
    public boolean checkExistence(UUID id) {
        return productRepository.existsById(id);
    }

    @Override
    @Transactional
    public ProductDTO create(ProductDTO productDTO) {      
        if (productDTO.getProductName().isEmpty()) {
            logger.warning("Erro. String Vazia");
            throw new InvalidNameException("Nome não pode ser vazio");
        }

        if (productDTO.getPrice() != null && productDTO.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
        	logger.warning("Erro. Preço deve ser maior que zero");
            throw new InvalidPriceException(productDTO.getPrice());
        }
        
        if (productDTO.getPrice() == null) {
        	logger.warning("Erro. Preço deve ser maior que zero");
            throw new InvalidPriceException(productDTO.getPrice());
        }

        Product product = productMapper.toEntity(productDTO);
        Product productSaved = productRepository.save(product);
        
        logger.info("Produto criado com ID: " + productSaved.getIdProduct());
        return productMapper.toDTO(productSaved);
    }

    @Override
    @Transactional
    public ProductDTO update(UUID id, ProductDTO productDTO) {
        Product productExists = productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException(id));
        
        if (productDTO.getProductName().isEmpty()) {
            logger.warning("Erro. String Vazia");
            throw new InvalidNameException("Nome não pode ser vazio");
        }
        
        if (productDTO.getPrice() != null && productDTO.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
        	logger.warning("Erro. Preço deve ser maior que zero");
            throw new InvalidPriceException(productDTO.getPrice());
        }
        
        if (productDTO.getPrice() == null) {
        	logger.warning("Erro. Preço deve ser maior que zero");
            throw new InvalidPriceException(productDTO.getPrice());
        }

        productMapper.updateEntityFromDTO(productDTO, productExists);
        Product updatedProduto = productRepository.save(productExists);
        
        logger.info("Produto com ID: " + updatedProduto.getIdProduct() + " atualizado");
        return productMapper.toDTO(updatedProduto);
    }

    @Override
    @Transactional
    public ProductDTO partiallyUpdate(UUID id, Map<String, Object> field) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException(id));

        field.forEach((fields, valor) -> {
            switch (fields) {
                case "productName" -> product.setProductName((String) valor);
                case "price" -> {
                    BigDecimal newPrice = new BigDecimal(valor.toString());
                    if (newPrice.compareTo(BigDecimal.ZERO) <= 0) {
                        throw new InvalidPriceException(newPrice);
                    }
                    product.setPrice(newPrice);
                }
                default -> throw new InvalidFieldUpdateException(fields);
            }
        });

        return productMapper.toDTO(productRepository.save(product));
    }

    @Override
    @Transactional
    public void delete(UUID id) {
    	
    	logger.info("Verificando a existência do produto para excluir");
        if (!productRepository.existsById(id)) {
        	logger.warning("Erro. Produto não encontrado");
            throw new ProductNotFoundException(id);
        }
        
        logger.info("Produto excluído");
        productRepository.deleteById(id);
    }
}