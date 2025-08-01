package com.nht.orderservice.mapper;

import com.nht.orderservice.dto.OrderLineDto;
import com.nht.orderservice.entity.OrderLine;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderLineMapper {
     OrderLineDto orderLineToOrderLineDto(OrderLine orderLine);
     OrderLine orderLineDtoToOrderLine(OrderLineDto orderLineDto);
     List<OrderLine> toEntityList(List<OrderLineDto> orderLineDtos);
     List<OrderLineDto> toDtoList(List<OrderLine> orderLines);
}
