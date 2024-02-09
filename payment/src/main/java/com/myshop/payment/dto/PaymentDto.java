package com.myshop.payment.dto;

import com.myshop.commonDtos.events.enums.PaymentStatus;
import lombok.Builder;

@Builder
public record PaymentDto(Integer paymentId,
                         String orderId,
                         String paymentIntentId,
                         String description,
                         PaymentStatus paymentStatus) {


}
