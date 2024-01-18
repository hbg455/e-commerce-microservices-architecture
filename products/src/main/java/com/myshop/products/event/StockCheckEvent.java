package com.myshop.products.event;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class StockCheckEvent {

    public Integer productId;
}
