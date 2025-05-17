package com.danilodps.product.controllers;

import com.danilodps.product.dtos.ProductDTO;
import com.danilodps.product.services.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Produtos", description = "Operações para gerenciamento de produtos")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @Operation(
        summary = "Criar um novo produto",
        description = "Cadastra um produto no sistema",
        responses = {
            @ApiResponse(responseCode = "201", description = "Produto criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
        }
    )
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        ProductDTO produtoCriado = productService.create(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoCriado);
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Buscar produto por ID",
        description = "Retorna os detalhes de um produto específico",
        responses = {
            @ApiResponse(responseCode = "200", description = "Produto encontrado"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
        }
    )
    public ResponseEntity<ProductDTO> getProductById(
        @Parameter(description = "ID do produto", example = "123e4567-e89b-12d3-a456-426614174000")
        @PathVariable UUID id) {
        ProductDTO produto = productService.getById(id);
        return ResponseEntity.ok(produto);
    }

    @GetMapping
    @Operation(
        summary = "Listar todos os produtos",
        description = "Retorna uma lista com todos os produtos cadastrados",
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista de produtos retornada")
        }
    )
    public ResponseEntity<List<ProductDTO>> findAllProducts() {
        List<ProductDTO> produtos = productService.findAll();
        return ResponseEntity.ok(produtos);
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Atualizar produto",
        description = "Substitui todos os dados de um produto existente",
        responses = {
            @ApiResponse(responseCode = "200", description = "Produto atualizado"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
        }
    )
    public ResponseEntity<ProductDTO> updateProduct(
        @Parameter(description = "ID do produto", example = "123e4567-e89b-12d3-a456-426614174000")
        @PathVariable UUID id,
        @RequestBody ProductDTO productDTO) {
        ProductDTO produtoAtualizado = productService.update(id, productDTO);
        return ResponseEntity.ok(produtoAtualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Excluir produto",
        description = "Remove um produto do sistema",
        responses = {
            @ApiResponse(responseCode = "204", description = "Produto excluído"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
        }
    )
    public ResponseEntity<Void> deleteProduct(
        @Parameter(description = "ID do produto", example = "123e4567-e89b-12d3-a456-426614174000")
        @PathVariable UUID id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @PatchMapping("/{id}")
    @Operation(
        summary = "Atualização parcial de produto",
        description = "Atualiza campos específicos de um produto",
        responses = {
            @ApiResponse(responseCode = "200", description = "Produto atualizado parcialmente"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
        }
    )
    public ResponseEntity<ProductDTO> partiallyUpdate(
        @Parameter(description = "ID do produto", example = "123e4567-e89b-12d3-a456-426614174000")
        @PathVariable UUID id,
        @RequestBody Map<String, Object> campos) {
        ProductDTO atualizado = productService.partiallyUpdate(id, campos);
        return ResponseEntity.ok(atualizado);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    @Operation(
        summary = "Verificar existência de produto",
        description = "Verifica se um produto existe no sistema",
        responses = {
            @ApiResponse(responseCode = "200", description = "Produto existe"),
            @ApiResponse(responseCode = "404", description = "Produto não existe")
        }
    )
    public ResponseEntity<?> checkExistence(
        @Parameter(description = "ID do produto", example = "123e4567-e89b-12d3-a456-426614174000")
        @PathVariable UUID id) {
        return productService.checkExistence(id) ?
            ResponseEntity.ok().build() :
            ResponseEntity.notFound().build();
    }

    @RequestMapping(method = RequestMethod.OPTIONS)
    @Operation(hidden = true) // Oculta do Swagger UI
    public ResponseEntity<?> options() {
        return ResponseEntity.noContent()
            .header("Allow", "GET, POST, PUT, PATCH, DELETE, HEAD, OPTIONS")
            .header("Access-Control-Allow-Methods", "GET, POST, PUT, PATCH, DELETE, OPTIONS")
            .header("Access-Control-Allow-Origin", "*")
            .build();
    }
}