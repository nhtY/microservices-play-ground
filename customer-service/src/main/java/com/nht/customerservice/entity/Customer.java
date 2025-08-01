package com.nht.customerservice.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "customers",
        indexes = @Index(name = "idx_user_id", columnList = "user_id")
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CustomerType type;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private CustomerDetail customerDetail;

    // Reference to an external User managed by the User microservice
    @Column(name = "user_id", nullable = false)
    private Long userId;
}
