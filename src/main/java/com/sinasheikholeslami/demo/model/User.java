package com.sinasheikholeslami.demo.model;

import java.time.LocalDate;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;

    @Size(min=2, message="Name should have at least 2 characters")
    private String name;

    @Past(message="Date of birth should be in the past")
    private LocalDate birthDate;
}
