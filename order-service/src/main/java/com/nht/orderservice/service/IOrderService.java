package com.nht.orderservice.service;

import com.nht.orderservice.dto.OrderDto;
import com.nht.orderservice.dto.controller.RequestCreateOrder;
import com.nht.orderservice.dto.controller.RequestCreateOrderLine;
import com.nht.orderservice.entity.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IOrderService {
    /**
     * Create a new order.
     *
     * @param orderDto the order to create
     * @return the created order
     */
    OrderDto createOrder(RequestCreateOrder orderDto);

    OrderDto addOrderLineToOrder(Long orderId, RequestCreateOrderLine requestCreateOrderLine);

    OrderDto removeOrderLineFromOrder(Long orderId, Long orderLineId);

    OrderDto confirmOrder(Long orderId);

    /**
     * Get an order by its ID.
     *
     * @param id the ID of the order
     * @return the order with the specified ID
     */
    OrderDto findById(Long id);

    OrderDto findByOwnerId(Long ownerId);

    List<OrderDto> findAll();

    Page<OrderDto> findAll(Pageable pageable);

    OrderDto updateOrderStatus(Long id, OrderStatus status);

    /**
     * Delete an order by its ID.
     *
     * @param id the ID of the order to delete
     */
    void deleteOrder(Long id);
}
