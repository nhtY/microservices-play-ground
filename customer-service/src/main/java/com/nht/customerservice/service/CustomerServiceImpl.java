package com.nht.customerservice.service;

import com.nht.customerservice.dto.CustomerDto;
import com.nht.customerservice.entity.Customer;
import com.nht.customerservice.exception.NotFoundException;
import com.nht.customerservice.mapper.CustomerDetailMapper;
import com.nht.customerservice.mapper.CustomerMapper;
import com.nht.customerservice.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final CustomerDetailMapper customerDetailMapper;

    @Override
    @Transactional
    public CustomerDto create(CustomerDto customerDto) {
        final Customer customer = customerMapper.toCustomer(customerDto);
        // set other side of one-to-one relationship
        if (customerDto.customerDetail() != null) {
            customer.getCustomerDetail().setCustomer(customer);
        }

        return customerMapper.toCustomerDto(customerRepository.save(customer));
    }

    @Override
    public CustomerDto findById(Long id) {
        return customerRepository.findById(id)
                .map(customerMapper::toCustomerDto)
                .orElseThrow(() -> new NotFoundException("Customer not found"));
    }

    @Override
    public CustomerDto findByUserId(Long userId) {
        return customerRepository.findByUserId(userId)
                .map(customerMapper::toCustomerDto)
                .orElseThrow(() -> new NotFoundException("Customer not found"));
    }

    @Override
    @Transactional
    public CustomerDto update(CustomerDto customerDto) {
        return customerRepository.findById(customerDto.id())
                .map(customer -> {
                    customer.setName(customerDto.name());
                    customer.setType(customerDto.type());
                    customer.setUserId(customerDto.userId());
                    if (customerDto.customerDetail() != null) {
                        customer.setCustomerDetail(customerDetailMapper.toCustomerDetail(customerDto.customerDetail()));
                    }
                    return customerRepository.save(customer);
                }).map(customerMapper::toCustomerDto).orElseThrow(() -> new NotFoundException("Customer not found"));
    }

    @Override
    public void delete(Long id) {
        customerRepository.deleteById(id);
    }

    @Override
    public List<CustomerDto> findAll() {
        return customerRepository.findAll().stream()
                .map(customerMapper::toCustomerDto)
                .toList();
    }

    @Override
    public Page<CustomerDto> findAll(Pageable pageable) {
        return customerRepository.findAll(pageable)
                .map(customerMapper::toCustomerDto);
    }
}
