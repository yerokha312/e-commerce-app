package dev.yerokha.ecommerce.kafka;

import dev.yerokha.ecommerce.customer.CustomerResponse;
import dev.yerokha.ecommerce.order.PaymentMethod;
import dev.yerokha.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}
