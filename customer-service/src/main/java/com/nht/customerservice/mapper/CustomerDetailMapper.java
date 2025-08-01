package com.nht.customerservice.mapper;

import com.nht.customerservice.dto.CustomerDetailDto;
import com.nht.customerservice.entity.CustomerDetail;
import org.mapstruct.Mapper;


@Mapper
public interface CustomerDetailMapper {

    CustomerDetailDto toCustomerDetailDto(CustomerDetail customerDetail);
    CustomerDetail toCustomerDetail(CustomerDetailDto customerDetailDto);
}
