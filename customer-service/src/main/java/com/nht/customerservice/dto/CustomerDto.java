package com.nht.customerservice.dto;

import com.nht.customerservice.entity.CustomerType;

public record CustomerDto (Long id, String name, CustomerType type, Long userId, CustomerDetailDto customerDetail) {
}
