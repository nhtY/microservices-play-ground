package com.nht.customerservice.service;

import com.nht.customerservice.dto.CustomerDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICustomerService {
    CustomerDto create(CustomerDto customerDto);
    CustomerDto findById(Long id);
    CustomerDto findByUserId(Long userId);
    CustomerDto update(CustomerDto customerDto);
    void delete(Long id);
    List<CustomerDto> findAll();
    Page<CustomerDto> findAll(Pageable pageable);
}
