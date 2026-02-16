package com.pedro.health.dtos.person;

import com.pedro.health.domains.Person;
import com.pedro.health.dtos.document.ReturnDocumentDto;

import java.util.List;

public record ReturnPersonDto(
        String id,
        String name,
        String phone,
        List<ReturnDocumentDto> documents) {
    public ReturnPersonDto(Person person) {
        this(person.getId(), person.getName(), person.getPhone(),person.getDocuments().stream().map(ReturnDocumentDto::new).toList());
    }
}
