package com.nht.customerservice.repository;

import com.nht.customerservice.entity.CustomerDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerDetailRepository extends JpaRepository<CustomerDetail, Long> {
    CustomerDetail findByCustomerId(Long customerId);
}
