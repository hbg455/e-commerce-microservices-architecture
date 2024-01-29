package com.myshop.order.config;

import com.myshop.commonDtos.dto.OrderRequestDto;
import com.myshop.commonDtos.events.OrderEvent;
import com.myshop.commonDtos.events.OrderStatus;
import com.myshop.commonDtos.events.StockAvailabilityStatus;
import com.myshop.order.entities.Order;
import com.myshop.order.repositories.OrderRepository;
import com.myshop.order.services.OrderStatusPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
public class OrderStatusUpdateHandler {


    private final OrderRepository orderRepository;
    private final OrderStatusPublisher publisher;

    @Transactional
    public void updateOrder(UUID id, Consumer<Order> consumer) {
        orderRepository.findByOrderNumber(id).ifPresent(consumer.andThen(this::updateOrder));
    }

    private void updateOrder(Order order) {
//        System.out.println("Order status updated to " + order.getOrderStatus());
//        OrderRequestDto orderRequestDto = null;
//        publisher.publishOrderEvent(orderRequestDto.orderRequestDto , order.getOrderStatus());


    }
}
