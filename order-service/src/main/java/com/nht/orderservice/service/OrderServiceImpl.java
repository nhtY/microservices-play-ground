package com.nht.orderservice.service;

import com.nht.orderservice.dto.OrderDto;
import com.nht.orderservice.dto.OrderLineDto;
import com.nht.orderservice.dto.controller.RequestCreateOrder;
import com.nht.orderservice.dto.controller.RequestCreateOrderLine;
import com.nht.orderservice.entity.Order;
import com.nht.orderservice.entity.OrderLine;
import com.nht.orderservice.entity.OrderStatus;
import com.nht.orderservice.mapper.OrderMapper;
import com.nht.orderservice.repository.OrderLineRepository;
import com.nht.orderservice.repository.OrderRepository;
import exceptions.InvalidPageRequest;
import exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderLineRepository orderLineRepository;

    @Override
    public OrderDto createOrder(RequestCreateOrder requestCreateOrder) {
        // Convert OrderDto to Order entity
        final Order order = orderMapper.toEntity(requestCreateOrder);
        order.setStatus(OrderStatus.IN_SHOPPING_CART);

        // TODO: check if products exist in product-service...

        // calculate total amount
        double totalAmount = 0.0;
        for (OrderLine orderLine : order.getOrderLines()) {
            totalAmount += orderLine.getPricePerUnit() * orderLine.getQuantity();
        }
        order.setTotalAmount(totalAmount);

        // Save the order entity to the database
        final Order savedOrder = orderRepository.save(order);

        // Convert the saved Order entity back to OrderDto
        return orderMapper.orderToOrderDto(savedOrder);
    }

    @Override
    public OrderDto addOrderLineToOrder(Long ownerId, RequestCreateOrderLine requestCreateOrderLine) {
        // check if order exists
        final Optional<Order> orderFound = orderRepository.findByOwnerId(ownerId);

        if (orderFound.isEmpty()) {
            // create a new order
            final RequestCreateOrder requestCreateOrder = new RequestCreateOrder(ownerId, List.of(requestCreateOrderLine.productId()));
            return createOrder(requestCreateOrder);
        }

        final Order order = orderFound.get();

        // check if order is in shopping cart
        if (order.getStatus() != OrderStatus.IN_SHOPPING_CART) {
            throw new IllegalStateException("Order is not in shopping cart: " + order.getId());
        }

        // TODO: check if product exists in product-service
        //  Then return the product info to use here.

        // Create a new OrderLine entity
        final OrderLine orderLine = new OrderLine();
        orderLine.setOrder(order);
        orderLine.setProductId(requestCreateOrderLine.productId());
        orderLine.setQuantity(requestCreateOrderLine.quantity());

        // Set the price per unit to 0.0 for now
        orderLine.setPricePerUnit(0.0);

        // Set the product name to a placeholder value
        orderLine.setProductName("Placeholder Product Name"); // Replace with actual product name retrieval logic

        // increase total amount
        order.setTotalAmount(order.getTotalAmount() + (orderLine.getPricePerUnit() * orderLine.getQuantity()));

        // Save the updated order entity to the database
        return orderMapper.orderToOrderDto(orderRepository.save(order));
    }

    @Override
    public OrderDto removeOrderLineFromOrder(Long orderId, Long orderLineId) {
        final Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found: " + orderId));
        // check if order is in shopping cart
        if (order.getStatus() != OrderStatus.IN_SHOPPING_CART) {
            throw new IllegalStateException("Order is not in shopping cart: " + orderId);
        }
        // Find the order line by ID
        final OrderLine orderLine = orderLineRepository.findById(orderLineId)
                .orElseThrow(() -> new NotFoundException("Order line not found: " + orderLineId));

        if (!orderId.equals(orderLine.getOrder().getId())) {
            throw new IllegalStateException("Order line does not belong to the order: " + orderLineId);
        }

        // if there is only one order line in the order, delete the order
        if (order.getOrderLines().size() == 1) {
            orderRepository.delete(order);
            return null;
        }

        // Decrease the total amount of the order
        order.setTotalAmount(order.getTotalAmount() - (orderLine.getPricePerUnit() * orderLine.getQuantity()));
        // Delete the order line entity from the database
        orderLineRepository.delete(orderLine);
        // Save the updated order entity to the database
        final Order updatedOrder = orderRepository.save(order);
        // Convert the updated Order entity to OrderDto
        return orderMapper.orderToOrderDto(updatedOrder);
    }

    @Override
    public OrderDto confirmOrder(Long orderId) {
        // Find the order by ID
        final Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found: " + orderId));

        // Check if the order is in the shopping cart
        if (order.getStatus() != OrderStatus.IN_SHOPPING_CART) {
            throw new IllegalStateException("Order is not in shopping cart: " + orderId);
        }

        // Update the order status to PAID
        order.setStatus(OrderStatus.PAID);

        // Save the updated order entity to the database
        final Order updatedOrder = orderRepository.save(order);

        // TODO: check if inventory has the amounts then decrease the inventory of the products in the order.

        // Convert the updated Order entity to OrderDto
        return orderMapper.orderToOrderDto(updatedOrder);
    }

    @Override
    public OrderDto findById(Long id) {
        // Find the order by ID
        final Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found: " + id));

        // Convert the Order entity to OrderDto
        return orderMapper.orderToOrderDto(order);
    }

    @Override
    public OrderDto findByOwnerId(Long ownerId) {
        // Find the order by owner ID
        final Order order = orderRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new NotFoundException("Order not found for owner: " + ownerId));

        // Convert the Order entity to OrderDto
        return orderMapper.orderToOrderDto(order);
    }

    @Override
    public List<OrderDto> findAll() {
        // Find all orders
        final List<Order> orders = orderRepository.findAll();

        // Convert the list of Order entities to a list of OrderDto
        return orderMapper.toDtoList(orders);
    }

    @Override
    public Page<OrderDto> findAll(Pageable pageable) {
        // check page arguments throw exception if invalid
        if (pageable.getPageNumber() < 0 || pageable.getPageSize() <= 0) {
            throw new InvalidPageRequest("Invalid page number or size");
        }

        // Find all orders with pagination
        final Page<Order> orderPage = orderRepository.findAll(pageable);

        // Convert the Page of Order entities to a Page of OrderDto
        return orderPage.map(orderMapper::orderToOrderDto);
    }

    @Override
    public OrderDto updateOrderStatus(Long id, OrderStatus status) {
        // Find the order by ID
        final Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found: " + id));

        // Update the order status
        order.setStatus(status);

        // Save the updated order entity to the database
        final Order updatedOrder = orderRepository.save(order);

        // Convert the updated Order entity to OrderDto
        return orderMapper.orderToOrderDto(updatedOrder);
    }

    @Override
    public void deleteOrder(Long id) {
        // Find the order by ID
        final Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found: " + id));

        // Delete the order entity from the database
        orderRepository.delete(order);
    }
}
