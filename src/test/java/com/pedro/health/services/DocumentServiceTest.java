package com.pedro.health.services;

import com.pedro.health.domains.Document;
import com.pedro.health.domains.Person;
import com.pedro.health.dtos.document.CreateDocumentDto;
import com.pedro.health.dtos.document.ReturnDocumentDto;
import com.pedro.health.dtos.person.ReturnPersonDto;
import com.pedro.health.dtos.person.UpdatePersonDto;
import com.pedro.health.enumerated.DocumentType;
import com.pedro.health.infra.exceptions.document.DocumentNotFoundException;
import com.pedro.health.infra.exceptions.person.PersonNotFoundException;
import com.pedro.health.repositories.DocumentRepository;
import com.pedro.health.repositories.PersonRepository;
import com.pedro.health.service.DocumentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DocumentServiceTest {

    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private DocumentService documentService;

    private Document document;

    @BeforeEach
    void setUp(){
        document = new Document();
        document.setId("1");
        document.setDocumentType(DocumentType.RG);
        document.setDescription("123456789");
        Person person = new Person();
        person.setName("pedro");
        person.setId("1");
        document.setPerson(person);

    }


    @Test
    @DisplayName("Must Return All Documents By User Id Successfully")
    void returnAllDocumentsByPersonId(){

        when(personRepository.findById("1")).thenReturn(Optional.ofNullable(document.getPerson()));

        when(documentRepository.findAllByPersonId("1")).thenReturn(List.of(document));

        List<ReturnDocumentDto> returnDocumentDto = documentService.returnAllDocumentsByPersonId("1");
        assertEquals(new ReturnDocumentDto(document),returnDocumentDto.getFirst());
        verify(documentRepository,times(1)).findAllByPersonId("1");
    }

    @Test
    @DisplayName("Must Thrown Exception When Search For Documents With Invalid Person Id")
    void returnAllDocumentsByPersonIdError(){
        PersonNotFoundException exception = assertThrows(PersonNotFoundException.class, ()-> documentService.returnAllDocumentsByPersonId("1"));
        assertEquals("Person with Id: 1 Not Found Or Deleted, Please Try Again!", exception.getMessage());
        verify(personRepository,times(1)).findById("1");
    }

    @Test
    @DisplayName("Must Update An Document Successfully")
    void updateDocument(){
        CreateDocumentDto update = new CreateDocumentDto(DocumentType.CPF,"22222222222");

        when(documentRepository.findById(document.getId())).thenReturn(Optional.of(document));
        when(documentRepository.save(any(Document.class))).thenReturn(document);

        ReturnDocumentDto result = documentService.updateDocument(document.getId(),update);

        assertEquals(result.documentType(),update.documentType());
        verify(documentRepository,times(1)).findById(document.getId());
        verify(documentRepository,times(1)).save(any(Document.class));
    }

    @Test
    @DisplayName("Must Thrown Exception When Try Update Document With Invalid Person Id")
    void updateDocumentError(){
        CreateDocumentDto update = new CreateDocumentDto(DocumentType.CPF,"22222222222");

        when(documentRepository.findById(document.getId())).thenReturn(Optional.empty());

        DocumentNotFoundException exception = assertThrows(DocumentNotFoundException.class,()->documentService.updateDocument(document.getId(),update));

        assertEquals("Document with Id: " + document.getId()+ " Not Found Or Deleted, Please Try Again!",exception.getMessage());
        verify(documentRepository,times(1)).findById(document.getId());
        verify(documentRepository,never()).save(any(Document.class));
    }


}
