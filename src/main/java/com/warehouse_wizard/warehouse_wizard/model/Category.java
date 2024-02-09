package com.warehouse_wizard.warehouse_wizard.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity()
@Table(name = "category")
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int category_id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description")
    private String description;
}
