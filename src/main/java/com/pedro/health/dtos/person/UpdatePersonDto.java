package com.pedro.health.dtos.person;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UpdatePersonDto(
        @NotBlank(message = "Name is Required")
        String name,
        @NotBlank(message = "Phone is Required")
        String phone,
        @NotNull(message = "BirthDate is Required")
        LocalDate birthDate) {
}
