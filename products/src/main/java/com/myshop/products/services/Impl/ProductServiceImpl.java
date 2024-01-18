package com.myshop.products.services.Impl;

import com.myshop.products.dto.ProductDto;
import com.myshop.products.entities.Product;
import com.myshop.products.event.StockCheckEvent;
import com.myshop.products.helper.ProductMappingHelper;
import com.myshop.products.repositories.ProductRepository;
import com.myshop.products.services.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public List<ProductDto> findAll() {
        log.info("*** ProductDto List, service; fetch all products *");

        return  productRepository.findAll()
                .stream()
                .map( ProductMappingHelper::map)
                .distinct()
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public ProductDto findById(Integer productId) {
        return null;
    }

    @Override
    public ProductDto save(ProductDto productDto) {
        log.info("*** ProductDto, service; save product *");
        Product product = ProductMappingHelper.map(productDto);
        product.setCreatedAt(Instant.now());
        return ProductMappingHelper.map(productRepository
                .save(product));
    }

    @Override
    public ProductDto update(ProductDto productDto) {
        return null;
    }

    @Override
    public ProductDto update(Integer productId, ProductDto productDto) {
        return null;
    }

    @Override
    public void deleteById(Integer productId) {

    }
    public boolean isProductAvailable(Integer productId) {
        // Logic to check if the product is available
        // You can use the existing method or add additional logic as needed
        // ...

        // For now, let's assume the product is available
        return true;
    }

    public void publishStockCheckEvent(Integer productId) {
        // Publish a stock check event to Kafka

        log.info(format("sending message to check-Stock-stream Topic::%s",productId));

        kafkaTemplate.send("stock-check-events", StockCheckEvent.builder().productId(productId).build());

    }
}
