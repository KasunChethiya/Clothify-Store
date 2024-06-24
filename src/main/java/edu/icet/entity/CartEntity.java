package edu.icet.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartEntity {
    private Integer productId;
    private String title;
    private Integer qty;
    private Double total;
}
