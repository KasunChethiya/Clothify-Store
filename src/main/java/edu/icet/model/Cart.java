package edu.icet.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Cart {
    private String id;
    private Integer productId;
    private String title;
    private Integer qty;
    private Double total;
}