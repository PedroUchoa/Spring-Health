package com.pedro.health.dtos.document;

import com.pedro.health.enumerated.DocumentType;
import jakarta.validation.constraints.NotBlank;

public record CreateDocumentDto(
        @NotBlank(message = "Document Type Is Required")
        DocumentType documentType,
        @NotBlank(message = "Description Type Is Required")
        String description
) {
}
