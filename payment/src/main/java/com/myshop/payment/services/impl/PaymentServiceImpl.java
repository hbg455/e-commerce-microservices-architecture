package com.myshop.payment.services.impl;

import com.myshop.commonDtos.events.enums.PaymentStatus;
import com.myshop.payment.dto.CreatePayment;
import com.myshop.payment.dto.PaymentDto;
import com.myshop.payment.entities.Payment;
import com.myshop.payment.exceptions.wrapper.PaymentNotFoundException;
import com.myshop.payment.helper.PaymentMappingHelper;
import com.myshop.payment.repositories.PaymentRepository;
import com.myshop.payment.services.PaymentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    @Override
    public List<PaymentDto> findAll() {
        return paymentRepository.findAll()
                .stream()
                .map(PaymentMappingHelper::mapToDto)
                .distinct()
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public PaymentDto findById(Integer paymentId) {
        log.info("*** Payment, service; fetch Payment by id *");
        return this.paymentRepository.findById(paymentId)
                .map(PaymentMappingHelper::mapToDto)
                .orElseThrow(() -> new PaymentNotFoundException(String.format("Payment with id: %d not found", paymentId)));    }

    @Override
    public PaymentDto save(CreatePayment paymentDto , String paymentIntentId) {
        log.info("*** Payment, service; save Payment *");
        Payment payment = PaymentMappingHelper.maptoPayment(paymentDto);
        payment.setCreatedAt(Instant.now());
        payment.setPaymentIntentId(paymentIntentId);
        payment.setPaymentStatus(PaymentStatus.PAYMENT_FAILED);
        paymentRepository.save(payment);
        return PaymentMappingHelper.mapToDto(payment);
    }

    @Override
    public PaymentDto update(PaymentDto paymentDto) {
        return null;
    }

    @Override
    public PaymentDto handlePayment(String paymentIntentId, String paymentStatus) {
        Payment payment = this.paymentRepository.findByPaymentIntentId(paymentIntentId);
        if (paymentStatus.equals("succeeded")) {
            payment.setPaymentStatus(PaymentStatus.PAYMENT_COMPLETED);
        }
        else if (paymentStatus.equals("failed")) {
            payment.setPaymentStatus(PaymentStatus.PAYMENT_FAILED);
        }

        return PaymentMappingHelper.mapToDto(payment) ;
    }

    @Override
    public void deleteById(Integer paymentId) {

    }
}
