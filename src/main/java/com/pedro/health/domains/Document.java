package com.pedro.health.domains;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pedro.health.dtos.document.CreateDocumentDto;
import com.pedro.health.enumerated.DocumentType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity(name = "Document")
@Table(name = "document")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@SQLRestriction(value = "is_active = true")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @Enumerated(EnumType.STRING)
    private DocumentType documentType;
    private String description;
    private Boolean isActive = true;
    @CreationTimestamp
    private LocalDateTime createdOn;
    @UpdateTimestamp
    private LocalDateTime updatedOn;
    private LocalDateTime deletedOn;
    @ManyToOne
    @JoinColumn(name = "personId")
    @JsonBackReference
    private Person person;

    public Document(CreateDocumentDto createDocumentDto) {
        this.documentType = createDocumentDto.documentType();
        this.description = createDocumentDto.description();
    }

    public void disable(){
        this.setIsActive(false);
        this.setDeletedOn(LocalDateTime.now());
    }


    public void update(CreateDocumentDto updateDocument) {
        this.setDocumentType(updateDocument.documentType());
        this.setDescription(updateDocument.description());
    }
}
