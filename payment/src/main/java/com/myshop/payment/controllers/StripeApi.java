package com.myshop.payment.controllers;

import com.myshop.payment.dto.CreatePayment;
import com.myshop.payment.dto.CreatePaymentResponse;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Slf4j
public class StripeApi {

    private final StripeService stripeService;


    @PostMapping("/create-payment-intent" )
    @ResponseBody
    public CreatePaymentResponse createPaymentIntent(@RequestBody CreatePayment createPayment) throws StripeException {

        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount(15 * 100L) //TODO: get this from createPayment :  the order amount
                        .setCurrency("gbp")
                        // In the latest version of the API, specifying the `automatic_payment_methods` parameter is optional because Stripe enables its functionality by default.
                        .setAutomaticPaymentMethods(
                                PaymentIntentCreateParams.AutomaticPaymentMethods
                                        .builder()
                                        .setEnabled(true)
                                        .build()
                        )
                        .build();

        // Create a PaymentIntent with the order amount and currency
        PaymentIntent paymentIntent = PaymentIntent.create(params);

        return new CreatePaymentResponse(paymentIntent.getClientSecret());

    }


}





