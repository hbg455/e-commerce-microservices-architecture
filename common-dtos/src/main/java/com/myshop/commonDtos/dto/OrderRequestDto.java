package com.myshop.commonDtos.dto;

import lombok.Builder;
import lombok.ToString;

@Builder
@ToString
public class OrderRequestDto {


    private String skuCode;
    private Integer amount;
    private Integer orderId;
}
