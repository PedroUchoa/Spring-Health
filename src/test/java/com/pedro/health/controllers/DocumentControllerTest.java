package com.pedro.health.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pedro.health.domains.Document;
import com.pedro.health.domains.Person;
import com.pedro.health.dtos.document.CreateDocumentDto;
import com.pedro.health.dtos.document.ReturnDocumentDto;
import com.pedro.health.enumerated.DocumentType;
import com.pedro.health.infra.exceptions.document.DocumentNotFoundException;
import com.pedro.health.infra.exceptions.person.PersonNotFoundException;
import com.pedro.health.service.DocumentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class DocumentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DocumentService documentService;

    ObjectMapper objectMapper = new ObjectMapper();

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
    @DisplayName("Must Return 200 Ok Searching Document By Person Id Id")
    void returnAllDocumentsByPersonIdSuccess() throws Exception {

        when(documentService.returnAllDocumentsByPersonId(document.getPerson().getId())).thenReturn(List.of(new ReturnDocumentDto(document)));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/document/{personId}",document.getPerson().getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(documentService,times(1)).returnAllDocumentsByPersonId(document.getPerson().getId());
    }

    @Test
    @DisplayName("Must Return 404 Not Found Searching Document By Person Id Id")
    void returnAllDocumentsByPersonIdError() throws Exception {

        when(documentService.returnAllDocumentsByPersonId(document.getPerson().getId())).thenThrow(new PersonNotFoundException(document.getPerson().getId()));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/document/{personId}",document.getPerson().getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(documentService,times(1)).returnAllDocumentsByPersonId(document.getPerson().getId());
    }

    @Test
    @DisplayName("Return 200 Ok When Update Document")
    void updateDocumentSuccess() throws Exception {
        CreateDocumentDto update = new CreateDocumentDto(DocumentType.CPF,"22222222222");
        ReturnDocumentDto dto = new ReturnDocumentDto(document);
        when(documentService.updateDocument(document.getId(),update)).thenReturn(dto);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/document/{id}",document.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(update)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(dto.id()));

        verify(documentService,times(1)).updateDocument(document.getId(),update);
    }

    @Test
    @DisplayName("Return 404 Not Found When Update Document")
    void updateDocumentError() throws Exception {
        CreateDocumentDto update = new CreateDocumentDto(DocumentType.CPF,"22222222222");
        when(documentService.updateDocument(document.getId(),update)).thenThrow(new DocumentNotFoundException(document.getId()));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/document/{id}",document.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(documentService,times(1)).updateDocument(document.getId(),update);
    }

}
