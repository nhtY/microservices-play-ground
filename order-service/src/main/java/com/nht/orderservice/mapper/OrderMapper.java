package com.nht.orderservice.mapper;

import com.nht.orderservice.dto.OrderDto;
import com.nht.orderservice.dto.controller.RequestCreateOrder;
import com.nht.orderservice.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
     OrderDto orderToOrderDto(Order order);
     Order orderDtoToOrder(OrderDto orderDto);
     List<Order> toEntityList(List<OrderDto> orderDtos);
     List<OrderDto> toDtoList(List<Order> orders);

     @Mapping(target = "id", ignore = true)
     @Mapping(target = "orderDate", ignore = true)
     @Mapping(target = "status", ignore = true)
     @Mapping(target = "totalAmount", ignore = true)
     @Mapping(target = "orderLines", ignore = true)
     Order toEntity(RequestCreateOrder requestCreateOrder);
}
