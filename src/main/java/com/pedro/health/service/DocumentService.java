package com.pedro.health.service;

import com.pedro.health.domains.Document;
import com.pedro.health.dtos.document.CreateDocumentDto;
import com.pedro.health.dtos.document.ReturnDocumentDto;
import com.pedro.health.infra.exceptions.document.DocumentNotFoundException;
import com.pedro.health.infra.exceptions.person.PersonNotFoundException;
import com.pedro.health.repositories.DocumentRepository;
import com.pedro.health.repositories.PersonRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private PersonRepository personRepository;

    @Cacheable(value = "document")
    public List<ReturnDocumentDto> returnAllDocumentsByPersonId(String personId){
        personRepository.findById(personId).orElseThrow(()->new PersonNotFoundException(personId));
        return documentRepository.findAllByPersonId(personId).stream().map(ReturnDocumentDto::new).toList();
    }

    @Transactional
    @CacheEvict(value = {"person","document"}, allEntries = true)
    public ReturnDocumentDto updateDocument(String documentId, CreateDocumentDto updateDocument){
        Document document = documentRepository.findById(documentId).orElseThrow(()-> new DocumentNotFoundException(documentId));
        document.update(updateDocument);
        documentRepository.save(document);
        return new ReturnDocumentDto(document);
    }

}
