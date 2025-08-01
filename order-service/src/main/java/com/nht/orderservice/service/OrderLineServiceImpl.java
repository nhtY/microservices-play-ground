package com.nht.orderservice.service;

import com.nht.orderservice.dto.OrderLineDto;
import com.nht.orderservice.entity.OrderLine;
import com.nht.orderservice.mapper.OrderLineMapper;
import com.nht.orderservice.repository.OrderLineRepository;
import exceptions.NotFoundException;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Validated
public class OrderLineServiceImpl implements IOrderLineService {

    private final OrderLineRepository orderLineRepository;
    private final OrderLineMapper orderLineMapper;

    @Override
    public OrderLineDto increaseOrderLineQuantity(Long orderLineId, @Min(1) Integer quantity) {
        final OrderLine orderLine = orderLineRepository.findById(orderLineId)
                .orElseThrow(() -> new NotFoundException("Order line not found"));

        // Update the quantity
        orderLine.setQuantity(orderLine.getQuantity() + quantity);

        // update the order total price
        orderLine.getOrder().setTotalAmount(
                orderLine.getOrder().getTotalAmount() + (orderLine.getPricePerUnit() * quantity)
        );

        // Save the updated order line
        final OrderLine updatedOrderLine = orderLineRepository.save(orderLine);
        // Convert the updated OrderLine entity to OrderLineDto
        return orderLineMapper.orderLineToOrderLineDto(updatedOrderLine);
    }

    @Override
    public OrderLineDto decreaseOrderLineQuantity(Long orderLineId, @Min(1) Integer quantity) {
        final OrderLine orderLine = orderLineRepository.findById(orderLineId)
                .orElseThrow(() -> new NotFoundException("Order line not found"));

        // Update the quantity
        orderLine.setQuantity(orderLine.getQuantity() - quantity);

        // update the order total price
        orderLine.getOrder().setTotalAmount(
                orderLine.getOrder().getTotalAmount() - (orderLine.getPricePerUnit() * quantity)
        );

        // Save the updated order line
        final OrderLine updatedOrderLine = orderLineRepository.save(orderLine);
        // Convert the updated OrderLine entity to OrderLineDto
        return orderLineMapper.orderLineToOrderLineDto(updatedOrderLine);
    }
}
