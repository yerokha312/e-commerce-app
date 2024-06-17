package dev.yerokha.ecommerce.order;

import dev.yerokha.ecommerce.customer.CustomerClient;
import dev.yerokha.ecommerce.customer.CustomerResponse;
import dev.yerokha.ecommerce.exception.BusinessException;
import dev.yerokha.ecommerce.kafka.OrderConfirmation;
import dev.yerokha.ecommerce.kafka.OrderProducer;
import dev.yerokha.ecommerce.orderLine.OrderLineRequest;
import dev.yerokha.ecommerce.orderLine.OrderLineService;
import dev.yerokha.ecommerce.product.ProductClient;
import dev.yerokha.ecommerce.product.PurchaseRequest;
import dev.yerokha.ecommerce.product.PurchaseResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;

    public Integer createOrder(OrderRequest request) {
        CustomerResponse customer = customerClient.findCustomerById(request.customerId())
                .orElseThrow(() ->
                        new BusinessException("Cannot create order: No Customer exists with provided id: " + request.customerId()));

        List<PurchaseResponse> purchasedProducts = productClient.purchaseProducts(request.products());

        Order order = repository.save(mapper.toOrder(request));

        for (PurchaseRequest product : request.products()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            product.productId(),
                            product.quantity()
                    )
            );
        }

        // todo start payment process



        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchasedProducts
                )
        );

        return order.getId();
    }

    public List<OrderResponse> findAll() {
        return repository.findAll().stream()
                .map(mapper::fromOrder)
                .toList();
    }

    public OrderResponse findById(Integer orderId) {
        return repository.findById(orderId)
                .map(mapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Order with ID %d not found", orderId)));
    }
}






























