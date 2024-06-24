package edu.icet.entity;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "ordered_items")
@Table(name = "ordered_items")
public class OrderedItemsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String orderId;
    private String productId;
    private int qty;
    private double amount;
}
