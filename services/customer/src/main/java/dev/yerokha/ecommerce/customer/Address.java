package dev.yerokha.ecommerce.customer;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Document
@Builder
@Validated
public class Address {
    private String street;
    private String houseNumber;
    private String zipCode;
}
