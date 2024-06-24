package edu.icet.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Getter
@Setter
@Entity(name = "users")
@Table(name = "users")
public class UserEntity {
    @Id
    private String id;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String address;
    private String phoneNo;
    private String email;
    private String role;
    private String password;
}
