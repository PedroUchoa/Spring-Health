package com.pedro.health.dtos.person;

import com.pedro.health.dtos.document.CreateDocumentDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record CreatePersonDto(
        @NotBlank(message = "Name is Required")
        String name,
        @NotBlank(message = "Phone is Required")
        String phone,
        @NotNull(message = "Birthdate is Required")
        LocalDate birthDate,
        List<CreateDocumentDto> documents) {
}
