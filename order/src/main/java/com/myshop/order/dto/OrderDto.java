package com.myshop.order.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OrderDto  implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long OrderId;

    private String orderNumber;

    @JsonProperty("orderLine")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<OrderLineDto> orderLineItemsList;

}
