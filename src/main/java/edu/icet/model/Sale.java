package edu.icet.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Sale {
    private String orderId;
    private String date;
    private String userId;
    private Double total;
}
