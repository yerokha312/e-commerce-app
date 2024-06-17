package dev.yerokha.ecommerce.payment;

import dev.yerokha.ecommerce.customer.CustomerResponse;
import dev.yerokha.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}
