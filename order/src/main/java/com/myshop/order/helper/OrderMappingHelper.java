package com.myshop.order.helper;

import com.myshop.order.dto.OrderDto;
import com.myshop.order.dto.OrderLineDto;
import com.myshop.order.entities.Order;
import com.myshop.order.entities.OrderLine;

import java.util.List;
import java.util.stream.Collectors;

public interface OrderMappingHelper {

    public static Order mapToOrder(final OrderDto orderDto) {

        List<OrderLine> orderLines = orderDto.getOrderLineItemsList().stream()
                .map(OrderMappingHelper::mapOrderLine)
                .collect(Collectors.toList());

        return Order.builder()
                .orderId(orderDto.getOrderId())
                .orderNumber(orderDto.getOrderNumber())
                .orderLineItemsList( orderLines)
                .build();
    }

    private static OrderLine mapOrderLine(OrderLineDto orderLineDTO) {
        return OrderLine.builder()
                .orderLineId(orderLineDTO.getOrderLineId())
                .skuCode(orderLineDTO.getSkuCode())
                .price(orderLineDTO.getPrice())
                .quantity(orderLineDTO.getQuantity())
                .build();
    }

    public static OrderDto mapToDto(final Order order) {
        List<OrderLineDto> orderLineDTOs = order.getOrderLineItemsList().stream()
                .map(OrderMappingHelper::mapOrderLineToDto)
                .collect(Collectors.toList());

        return OrderDto.builder()
                .orderId(order.getOrderId())
                .orderNumber(order.getOrderNumber())
                .orderLineItemsList(orderLineDTOs)
                .build();
    }

    private static OrderLineDto mapOrderLineToDto(OrderLine orderLine) {
        return OrderLineDto.builder()
                .orderLineId(orderLine.getOrderLineId())
                .skuCode(orderLine.getSkuCode())
                .price(orderLine.getPrice())
                .quantity(orderLine.getQuantity())
                .build();
    }
}
