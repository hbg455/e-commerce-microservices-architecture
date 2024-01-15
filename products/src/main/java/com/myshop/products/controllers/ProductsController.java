package com.myshop.products.controllers;

import com.myshop.products.dto.DtoCollectionResponse;
import com.myshop.products.dto.ProductDto;
import com.myshop.products.services.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@Slf4j
@RequiredArgsConstructor
public class ProductsController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<DtoCollectionResponse<ProductDto>> findAll() {
        log.info("*** ProductDto List, controller; fetch all products *");
        return ResponseEntity.ok(new DtoCollectionResponse<>(this.productService.findAll()));
    }

    @PostMapping
    public ResponseEntity<ProductDto> save(
            @RequestBody
            @NotNull(message = "Input must not be NULL!")
            @Valid final ProductDto productDto) {
        log.info("*** ProductDto, controller; save product *");
        return ResponseEntity.ok(this.productService.save(productDto));
    }
}
