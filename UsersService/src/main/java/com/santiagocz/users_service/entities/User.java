package com.santiagocz.users_service.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    @Pattern(regexp = "^[A-Za-zñÑáéíóúÁÉÍÓÚ\\s]+$", message = "The first name can only contain letters and spaces")
    private String firstName;

    @Column(name = "last_name")
    @Pattern(regexp = "^[A-Za-zñÑáéíóúÁÉÍÓÚ\\s]+$", message = "The last name can only contain letters and spaces")
    private String lastName;

    @Column(name = "phone_number")
    @Pattern(regexp = "\\d{9,11}", message = "The phone number must have between 9 and 11 digits")
    @NotBlank(message = "The phone number cannot be blank")
    private String phoneNumber;
}
