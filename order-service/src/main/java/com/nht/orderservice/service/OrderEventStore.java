package com.nht.orderservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nht.orderservice.entity.OrderEventRecord;
import com.nht.orderservice.repository.OrderEventRecordRepository;
import events.order.OrderEvent;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderEventStore {
    private final OrderEventRecordRepository orderEventRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    public void saveEvent(OrderEvent orderEvent) {
        final OrderEventRecord eventRecord = new OrderEventRecord();

        eventRecord.setOrderId(orderEvent.orderId());
        eventRecord.setEventId(orderEvent.eventId());
        eventRecord.setEventType(orderEvent.type().name());
        try {
            // Serialize the payload to JSON string
            eventRecord.setPayload(objectMapper.writeValueAsString(orderEvent.payload()));
        } catch (Exception e) {
            eventRecord.setPayload("{\"error\": \"Event payload serialization error. Event's payload could not be serialized\"}");
        }
        orderEventRepository.save(eventRecord);
    }
}
