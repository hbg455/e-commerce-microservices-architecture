package com.myshop.payment.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;

@Builder
public class CreatePayment {



    @SerializedName("amount")
    Double amount;

    @SerializedName("orderId")
    String orderId;

    @SerializedName("currency")
    String currency;

    @SerializedName("description")
    String description;





    public Double getAmount() {
        return amount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

}
