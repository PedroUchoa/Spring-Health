package com.pedro.health.dtos.document;

import com.pedro.health.domains.Document;
import com.pedro.health.enumerated.DocumentType;

public record ReturnDocumentDto(
        String id,
        DocumentType documentType,
        String description,
        String personName,
        String personId) {

    public ReturnDocumentDto(Document document){
     this(document.getId(),document.getDocumentType(),document.getDescription(),document.getPerson().getName(),document.getPerson().getId());
    }

}


