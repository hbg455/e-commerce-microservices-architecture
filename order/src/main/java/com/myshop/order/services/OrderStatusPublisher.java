package com.myshop.order.services;

import com.myshop.commonDtos.dto.OrderRequestDto;
import com.myshop.commonDtos.events.OrderEvent;
import com.myshop.commonDtos.events.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

@Service
@RequiredArgsConstructor
public class OrderStatusPublisher {


    private final Sinks.Many<OrderEvent> orderSinks;


    public void publishOrderEvent(OrderRequestDto orderRequestDto, OrderStatus orderStatus){
        OrderEvent orderEvent=new OrderEvent(orderRequestDto,orderStatus);
        orderSinks.tryEmitNext(orderEvent);
    }
}
