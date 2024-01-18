package com.myshop.order.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OrderLineDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long OrderLineId;

    private String skuCode;

    private BigDecimal price;

    private Integer quantity;
}
