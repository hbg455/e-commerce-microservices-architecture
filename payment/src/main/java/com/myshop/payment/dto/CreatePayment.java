package com.myshop.payment.dto;

import com.google.gson.annotations.SerializedName;

public class CreatePayment {
    @SerializedName("items")
    CreatePaymentItem[] items;

    public void setItems(CreatePaymentItem[] items) {
        this.items = items;
    }

    public CreatePaymentItem[] getItems() {
        return items;
    }
}
