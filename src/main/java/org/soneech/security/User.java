package org.soneech.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name="users")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Имя не может быть пустым")
    @Size(min = 2, max = 30, message = "Имя должно содержать от 2 до 30 символов")
    private String name;

    @NotEmpty(message = "Почта не может быть пустой")
    @Email(message = "Некорректая почта")
    private String email;

    @NotEmpty(message = "Пароль не может быть пустым")
    @Size(min = 6, message = "Пароль не может быть меньше 6 символов")
    private String password;
    @Transient
    private String passwordConfirm;
}
