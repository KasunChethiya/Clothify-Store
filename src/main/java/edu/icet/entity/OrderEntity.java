package edu.icet.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "orders")
@Table(name = "orders")
public class OrderEntity {
    @Id
    private String orderId;
    private String userId;
    private String date;
    private Double total;
}
