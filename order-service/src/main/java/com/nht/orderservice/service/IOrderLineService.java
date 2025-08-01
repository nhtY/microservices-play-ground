package com.nht.orderservice.service;

import com.nht.orderservice.dto.OrderLineDto;

public interface IOrderLineService {
    OrderLineDto increaseOrderLineQuantity(Long orderLineId, Integer quantity);

    OrderLineDto decreaseOrderLineQuantity(Long orderLineId, Integer quantity);
}
