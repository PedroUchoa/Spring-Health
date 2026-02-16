package com.pedro.health.dtos.person;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record UpdatePersonDto(
        @NotBlank(message = "Name is Required")
        String name,
        @NotBlank(message = "Phone is Required")
        String phone,
        @NotBlank(message = "BirthDate is Required")
        LocalDate birthDate) {
}
