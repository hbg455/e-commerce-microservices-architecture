package com.myshop.commonDtos.dto;

import lombok.Builder;

@Builder
public class StockRequestDto {

    private String skuCode;
    private Integer amount;
    private Integer orderId;
}
