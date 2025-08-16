package com.nht.orderservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;

@Entity
@Table(name = "order_events",
        indexes = {@Index(name = "idx_order_event_order_id", columnList = "orderId")},
        uniqueConstraints = {@UniqueConstraint(name = "uk_order_event_event_id", columnNames = "eventId")}
)
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class OrderEventRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;
    private String eventId; // UUID as String
    private String eventType;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String payload;     // serialized event record (JSON)

    @CreatedDate
    private Instant createdAt;
}
