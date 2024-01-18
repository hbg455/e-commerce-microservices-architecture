package com.myshop.order.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StockEvent {


    private String skuCode;
    private Integer quantity;

}
