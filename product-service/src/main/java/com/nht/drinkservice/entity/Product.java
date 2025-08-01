package com.nht.drinkservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "products",
        indexes = {
        @Index(name="idx_product_name", columnList = "name"),
        @Index(name="idx_product_category", columnList = "category_id"),
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(nullable = false)
    private Double price;

    @OneToMany(mappedBy = "product")
    private Set<Ingredient> ingredients;

    @OneToOne(mappedBy = "product")
    private Inventory inventory;

}

