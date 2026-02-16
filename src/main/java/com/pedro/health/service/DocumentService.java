package com.pedro.health.service;

import com.pedro.health.domains.Document;
import com.pedro.health.dtos.document.CreateDocumentDto;
import com.pedro.health.dtos.document.ReturnDocumentDto;
import com.pedro.health.repositories.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    public List<ReturnDocumentDto> returnAllDocumentsByPersonId(String personId){
        return documentRepository.findAllByPersonId(personId).stream().map(ReturnDocumentDto::new).toList();
    }

    public ReturnDocumentDto updateDocument(String documentId, CreateDocumentDto updateDocument){
        Document document = documentRepository.getReferenceById(documentId);
        document.update(updateDocument);
        documentRepository.save(document);
        return new ReturnDocumentDto(document);
    }

}
