package com.nht.orderservice.repository;

import com.nht.orderservice.entity.OrderEventRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderEventRecordRepository extends JpaRepository<OrderEventRecord, Long> {
}
