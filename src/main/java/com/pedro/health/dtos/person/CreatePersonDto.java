package com.pedro.health.dtos.person;

import com.pedro.health.dtos.document.CreateDocumentDto;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.List;

public record CreatePersonDto(
        @NotBlank(message = "Name is Required")
        String name,
        @NotBlank(message = "Phone is Required")
        String phone,
        @NotBlank(message = "Birthdate is Required")
        LocalDate birthDate,
        @NotBlank(message = "Documents are Required")
        List<CreateDocumentDto> documents) {
}
