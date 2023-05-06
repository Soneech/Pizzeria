package org.soneech.models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "address")
@Getter
@Setter
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Поле не может быть пустым")
    @Size(min=2, max=30, message = "Некорректное название города")
    private String city;

    @NotEmpty(message = "Поле не может быть пустым")
    @Size(min=2, max=150, message = "Некорректное название улицы")
    private String street;

    @NotEmpty(message = "Поле не может быть пустым")
    @Column(name = "house_number")
    private String houseNumber;

    private String details;
}
