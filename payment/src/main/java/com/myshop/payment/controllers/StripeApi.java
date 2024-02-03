package com.myshop.payment.controllers;

import com.myshop.payment.dto.StripeChargeDto;
import com.myshop.payment.dto.StripeTokenDto;
import com.myshop.payment.services.StripeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("public/stripe")
@AllArgsConstructor
@Slf4j
public class StripeApi {

    private final StripeService stripeService;


    @PostMapping("/card/token")
    @ResponseBody
    public StripeTokenDto createCardToken(@RequestBody StripeTokenDto model) {


        return stripeService.createCardToken(model);
    }

    @PostMapping("/charge")
    @ResponseBody
    public StripeChargeDto charge(@RequestBody StripeChargeDto model) {

        return stripeService.charge(model);
    }

}
