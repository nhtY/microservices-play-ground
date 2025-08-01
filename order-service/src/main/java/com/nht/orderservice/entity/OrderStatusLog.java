package com.nht.orderservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(
        name = "order_status_logs",
        indexes = @Index(name = "idx_order_id", columnList = "order_id")
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(nullable = false, updatable = false)
    private OrderStatus status;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private String createdAt;
}
