package com.myshop.order.controllers;

import com.myshop.order.dto.DtoCollectionResponse;
import com.myshop.order.dto.OrderDto;
import com.myshop.order.services.OrderServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@Slf4j
@RequiredArgsConstructor
public class OrderController {

    private final OrderServiceImpl orderService ;

    @PostMapping
    public ResponseEntity<OrderDto> save(
            @RequestBody
            @NotNull(message = "Input must not be NULL!")
            @Valid final OrderDto orderDto) {
        log.info("*** OrderDto, controller; save order *");
        return ResponseEntity.ok(this.orderService.save(orderDto));
    }

    @GetMapping
    public ResponseEntity<DtoCollectionResponse<OrderDto>> findAll() {
        log.info("*** OrderDto List, controller; fetch all orders *");
        return ResponseEntity.ok(new DtoCollectionResponse<>(this.orderService.findAll()));
    }
}
