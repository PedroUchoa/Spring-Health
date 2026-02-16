package com.pedro.health.dtos.person;

import com.pedro.health.dtos.document.CreateDocumentDto;

import java.time.LocalDate;
import java.util.List;

public record CreatePersonDto(
        String name,
        String phone,
        LocalDate birthDate,
        List<CreateDocumentDto> documents) {
}
