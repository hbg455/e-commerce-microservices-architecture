package com.myshop.products.services.Impl;

import com.myshop.commonDtos.dto.OrderRequestDto;
import com.myshop.commonDtos.dto.StockRequestDto;
import com.myshop.commonDtos.events.OrderEvent;
import com.myshop.commonDtos.events.StockAvailabilityStatus;
import com.myshop.commonDtos.events.StockEvent;
import com.myshop.products.dto.ProductDto;
import com.myshop.products.entities.Product;
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
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;


    @Override
    public List<ProductDto> findAll() {
        log.info("*** ProductDto List, service; fetch all products *");

        return productRepository.findAll()
                .stream()
                .map(ProductMappingHelper::map)
                .distinct()
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public ProductDto findById(Integer productId) {
        log.info("*** ProductDto, service; fetch product by id *");
        return this.productRepository.findBySkuCode("fedi")
                .map(ProductMappingHelper::map)
                .orElseThrow(() -> new RuntimeException(String.format("Product with id: %d not found", productId)));
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


    public StockEvent newOrderEvent(OrderEvent orderEvent) {
        OrderRequestDto orderRequestDto = orderEvent.getOrderRequestDto();

        StockRequestDto stockRequestDto = StockRequestDto.builder()
                .skuCode(orderRequestDto.getSkuCode())
                .amount(orderRequestDto.getAmount())
                .orderId(orderRequestDto.getOrderId())
                .build();


        return productRepository.findBySkuCode(orderRequestDto.getSkuCode())
                .filter(p -> p.getQuantity() > orderRequestDto.getAmount())
                .map(p -> {
                    p.setQuantity(p.getQuantity() - orderRequestDto.getAmount());
                    productRepository.save(p);
                    log.info("*** StockEvent, service; StockEvent ***", p);
                    return new StockEvent(stockRequestDto, StockAvailabilityStatus.AVAILABLE);
                }).orElse(new StockEvent(stockRequestDto, StockAvailabilityStatus.OUT_OF_STOCK));


    }


    public void cancelOrderEvent(OrderEvent orderEvent) {
    }
}
