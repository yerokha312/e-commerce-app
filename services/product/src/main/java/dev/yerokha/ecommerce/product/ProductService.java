package dev.yerokha.ecommerce.product;

import dev.yerokha.ecommerce.exception.ProductPurchaseException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    public Integer createProduct(ProductRequest request) {
        Product product = mapper.toProduct(request);
        return repository.save(product).getId();
    }

    public List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> requests) {
        List<Integer> productIds = requests.stream()
                .map(ProductPurchaseRequest::productId)
                .toList();
        List<Product> storedProducts = repository.findAllByIdInOrderById(productIds);
        if (productIds.size() != storedProducts.size()) {
            throw new ProductPurchaseException("One or more products not found");
        }

        List<ProductPurchaseRequest> storedRequests = requests.stream()
                .sorted(Comparator.comparing(ProductPurchaseRequest::productId))
                .toList();
        List<ProductPurchaseResponse> purchasedProducts = new ArrayList<>();
        for (int i = 0; i < storedProducts.size(); i++) {
            Product product = storedProducts.get(i);
            ProductPurchaseRequest request = storedRequests.get(i);
            if (product.getAvailableQuantity() < request.quantity()) {
                throw new ProductPurchaseException("Insufficient stock quantity for product with ID: " + request.productId());
            }
            double newAvailableQuantity = product.getAvailableQuantity() - request.quantity();
            product.setAvailableQuantity(newAvailableQuantity);
            repository.save(product);
            purchasedProducts.add(mapper.toProductPurchaseResponse(product, request.quantity()));
        }
        return purchasedProducts;
    }

    public ProductResponse findById(Integer productId) {
        return repository.findById(productId)
                .map(mapper::toProductResponse)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with the ID: " + productId));
    }

    public List<ProductResponse> findAll() {
        return repository.findAll().stream()
                .map(mapper::toProductResponse)
                .toList();
    }
}
