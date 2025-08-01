package com.nht.customerservice.mapper;

import com.nht.customerservice.dto.CustomerDto;
import com.nht.customerservice.entity.Customer;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {
     CustomerDto toCustomerDto(Customer customer);

     Customer toCustomer(CustomerDto customerDto);
}
