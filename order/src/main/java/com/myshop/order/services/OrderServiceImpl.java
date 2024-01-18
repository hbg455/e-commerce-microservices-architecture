package com.myshop.order.services;

import com.myshop.order.dto.OrderDto;
import com.myshop.order.entities.Order;
import com.myshop.order.event.StockEvent;
import com.myshop.order.exceptions.OrderRepository;
import com.myshop.order.helper.OrderMappingHelper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl {

    private final KafkaTemplate<String,String> kafkaTemplate;

    private final OrderRepository orderRepository ;


    public OrderDto save(OrderDto orderDto) {
        log.info("*** OrderDto, service; save order *");
        Order order = OrderMappingHelper.mapToOrder(orderDto);
        order.setCreatedAt(Instant.now());
        //kafkaTemplate.send("Pstock-check-events"  ,orderDto.getOrderNumber());
        List<StockEvent> stockEventList = order.getOrderLineItemsList().stream().map
                (l -> StockEvent.builder()
                        .skuCode(l.getSkuCode())
                        .quantity(l.getQuantity())
                        .build()).collect(Collectors.toList());
        for ( StockEvent s :stockEventList
             ) {
            Message<StockEvent> message = MessageBuilder
                    .withPayload(s)
                    .setHeader(KafkaHeaders.TOPIC ,"Pstock-check-events" )
                    .build();
            log.info(format("sending message to stock-stream Topic::%s",message.getPayload()));

            order.getOrderLineItemsList().forEach( l-> kafkaTemplate.send(message));


        }
        orderRepository.save(order);
        return orderDto;
    }

}
