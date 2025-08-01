package com.nht.orderservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "order_lines",
        indexes = @Index(name = "idx_order_id", columnList = "order_id")
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(nullable = false)
    private Long productId; // Logical reference to Product

    private String productName;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Double pricePerUnit;

    public Double calculateLineTotal() {
        return quantity * pricePerUnit;
    }
}

