package edu.icet.model;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Supplier {
    private String name;
    private String company;
    private String email;
    private Double address;
}