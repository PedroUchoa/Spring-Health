package com.pedro.health.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pedro.health.domains.Person;
import com.pedro.health.dtos.person.CreatePersonDto;
import com.pedro.health.dtos.person.ReturnPersonDto;
import com.pedro.health.dtos.person.UpdatePersonDto;
import com.pedro.health.infra.exceptions.person.PersonNotFoundException;
import com.pedro.health.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PersonService personService;

    private Person person;

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        person = new Person();
        person.setId("1");
        person.setName("Pedro");
        person.setPhone("11111111111");
        person.setBirthDate(LocalDate.now());
        person.setDocuments(new ArrayList<>());
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    @DisplayName("Must Return Status 201 Created When Create Person")
    void createPersonSuccess() throws Exception {
        CreatePersonDto createPersonDto = new CreatePersonDto(person.getName(), person.getPhone(), person.getBirthDate(), new ArrayList<>());

        when(personService.createPerson(createPersonDto)).thenReturn(new ReturnPersonDto(person));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createPersonDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        ArgumentCaptor<CreatePersonDto> captor = ArgumentCaptor.forClass(CreatePersonDto.class);

        verify(personService).createPerson(captor.capture());
        assertEquals(person.getName(), captor.getValue().name());
        assertEquals(person.getPhone(), captor.getValue().phone());
    }

    @Test
    @DisplayName("Must Return 200 Ok Searching All Person")
    void listAllPersonsSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/person"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(personService, times(1)).listAllPersons();
    }

    @Test
    @DisplayName("Return 200 Ok When Update Person")
    void updatePersonSuccess() throws Exception {
        UpdatePersonDto update = new UpdatePersonDto("Lucas", "22222222222", LocalDate.now().plusMonths(2));
        ReturnPersonDto dto = new ReturnPersonDto(person);

        when(personService.updatePerson(person.getId(), update)).thenReturn(dto);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/person/{id}", person.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(dto.id()))
                .andExpect(MockMvcResultMatchers.jsonPath("name").value(dto.name()));

        verify(personService, times(1)).updatePerson(person.getId(), update);

    }

    @Test
    @DisplayName("Return 404 Not Found When Update Person With Id Not Found")
    void updatePersonError() throws Exception {
        UpdatePersonDto update = new UpdatePersonDto("Lucas", "22222222222", LocalDate.now().plusMonths(2));

        when(personService.updatePerson(person.getId(), update)).thenThrow(new PersonNotFoundException(person.getId()));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/person/{id}", person.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(personService, times(1)).updatePerson(person.getId(), update);
    }

    @Test
    @DisplayName("Must Return 204 No Content When Trying To Delete Person By Id")
    void deletePersonSuccess() throws Exception {
        doNothing().when(personService).deletePerson(person.getId());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/person/{id}", person.getId()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        verify(personService, times(1)).deletePerson(person.getId());
    }

    @Test
    @DisplayName("Must Return 404 No Found When Trying To Delete Person By Id Not Found")
    void deletePersonError() throws Exception {
        doThrow(new PersonNotFoundException(person.getId())).when(personService).deletePerson(person.getId());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/person/{id}", person.getId()))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(personService,times(1)).deletePerson(person.getId());
    }


}
