package com.nht.customerservice.service;


import com.nht.customerservice.dto.CustomerDetailDto;
import com.nht.customerservice.dto.CustomerDto;
import com.nht.customerservice.entity.CustomerType;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@Transactional
class ICustomerServiceTest {

    @Autowired
    private ICustomerService customerService;

    private CustomerDto customerDto;
    private CustomerDetailDto customerDetailDto;

    @BeforeEach
    void setUp() {
        customerDetailDto = new CustomerDetailDto(null, "Address 1", "555", "email@email");
        customerDto = new CustomerDto(null, "Customer 1", CustomerType.GROCERY, 2L, customerDetailDto);
    }

    @Test
    void create() {

        CustomerDto response =  customerService.create(customerDto);

        assertEquals(customerDto.name(), response.name());
        assertEquals(customerDto.type(), response.type());
        assertEquals(customerDto.customerDetail().address(), response.customerDetail().address());

    }

    @Test
    void createWithoutDetails() {
        CustomerDto customerWithoutDetails = new CustomerDto(null, "Customer 1", CustomerType.GROCERY, 2L, null);
        CustomerDto response =  customerService.create(customerWithoutDetails);

        assertEquals(customerWithoutDetails.name(), response.name());
        assertEquals(customerWithoutDetails.type(), response.type());
        assertNull(response.customerDetail());

    }

    @Test
    void update() {

        CustomerDto response =  customerService.create(customerDto);

        assertEquals(customerDto.name(), response.name());
        assertEquals(customerDto.type(), response.type());
        assertEquals(customerDto.customerDetail().address(), response.customerDetail().address());

        CustomerDto updateDto = new CustomerDto(response.id(), "Customer 2", CustomerType.GROCERY, 2L, new CustomerDetailDto(response.customerDetail().id(), "Address 2", "555", "email@email"));

        CustomerDto updated = customerService.update(updateDto);

        assertEquals(updateDto.name(), updated.name());
        assertEquals(updateDto.customerDetail().address(), updated.customerDetail().address());

    }

    @Test
    void findAll() {

        customerService.create(customerDto);

        assertEquals(1, customerService.findAll().size());
    }

    @Test
    void findAllPaged() {

        customerService.create(customerDto);

        // create a pagable
        Pageable pageable = PageRequest.of(0, 10);

        assertEquals(1, customerService.findAll(pageable).getTotalElements());
        assertEquals(1, customerService.findAll(pageable).getTotalPages());
    }

    @Test
    void findById() {

        CustomerDto created = customerService.create(customerDto);

        assertEquals(customerDto.name(), customerService.findById(created.id()).name());
    }

    @Test
    void findByUserId() {

        CustomerDto created = customerService.create(customerDto);

        assertEquals(customerDto.name(), customerService.findByUserId(created.userId()).name());
        assertEquals(customerDto.userId(), customerService.findByUserId(created.userId()).userId());
    }

    @Test
    void delete() {

            CustomerDto response =  customerService.create(customerDto);

            assertEquals(customerDto.name(), response.name());
            assertEquals(customerDto.type(), response.type());
            assertEquals(customerDto.customerDetail().address(), response.customerDetail().address());

            customerService.delete(response.id());

            assertEquals(0, customerService.findAll().size());
    }

}


