package com.pedro.health.services;

import com.pedro.health.domains.Person;
import com.pedro.health.dtos.person.CreatePersonDto;
import com.pedro.health.dtos.person.ReturnPersonDto;
import com.pedro.health.dtos.person.UpdatePersonDto;
import com.pedro.health.infra.exceptions.person.PersonNotFoundException;
import com.pedro.health.repositories.PersonRepository;
import com.pedro.health.service.PersonService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService personService;

    private Person person;

    @BeforeEach
    void setUp(){
        person = new Person();
        person.setId("1");
        person.setName("Pedro");
        person.setPhone("11111111111");
        person.setBirthDate(LocalDate.now());
        person.setDocuments(new ArrayList<>());
    }

    @Test
    @DisplayName("Must Create a Person Successfully")
    void createPerson(){
        CreatePersonDto createPersonDto = new CreatePersonDto(person.getName(),person.getPhone(),person.getBirthDate(),new ArrayList<>());

        when(personRepository.save(any(Person.class))).thenReturn(person);

        personService.createPerson(createPersonDto);
        ArgumentCaptor<Person> personCaptor = ArgumentCaptor.forClass(Person.class);

        verify(personRepository).save(personCaptor.capture());

        Person capturedPerson = personCaptor.getValue();

        assertThat(capturedPerson.getName()).isEqualTo(createPersonDto.name());
        verify(personRepository,times(1)).save(any(Person.class));
    }

    @Test
    @DisplayName("Must Throw Exception When Creating Person With Null Value")
    void createPersonError(){
        CreatePersonDto createPersonDto = new CreatePersonDto(null,person.getPhone(),person.getBirthDate(),new ArrayList<>());

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<CreatePersonDto>> violations = validator.validate(createPersonDto);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Must Return All Persons")
    void getAllPersonsSuccess(){
        List<Person> personList = List.of(person);
        when(personRepository.findAll()).thenReturn(personList);

        List<ReturnPersonDto> returnPersonDto = personService.listAllPersons();

        verify(personRepository,times(1)).findAll();
        assertThat(returnPersonDto).usingRecursiveComparison().isEqualTo(personList);
    }

    @Test
    @DisplayName("Must Update Person")
    void updatePerson(){
        UpdatePersonDto updatePersonDto = new UpdatePersonDto("Lucas", "22222222222", LocalDate.now().plusMonths(2));

        when(personRepository.findById(person.getId())).thenReturn(Optional.of(person));
        when(personRepository.save(any(Person.class))).thenReturn(person);

        ReturnPersonDto result = personService.updatePerson(person.getId(),updatePersonDto);

        assertEquals(result.name(),updatePersonDto.name());
        verify(personRepository,times(1)).findById(person.getId());
        verify(personRepository,times(1)).save(any(Person.class));

    }

    @Test
    @DisplayName("Must Thrown Exception When Update Person With Invalid Id")
    void updatePersonError(){
        UpdatePersonDto updatePersonDto = new UpdatePersonDto("Lucas", "22222222222", LocalDate.now().plusMonths(2));

        when(personRepository.findById(person.getId())).thenReturn(Optional.empty());

        PersonNotFoundException exception = assertThrows(PersonNotFoundException.class,()->personService.updatePerson(person.getId(),updatePersonDto));

        assertEquals("Person with Id: " + person.getId() + " Not Found Or Deleted, Please Try Again!",exception.getMessage());
        verify(personRepository,times(1)).findById(person.getId());
        verify(personRepository,never()).save(any(Person.class));
    }

    @Test
    @DisplayName("Must Delete Person Successfully")
    void deletePerson(){
        when(personRepository.findById(person.getId())).thenReturn(Optional.of(person));
        personService.deletePerson(person.getId());

        ArgumentCaptor<Person> captor = ArgumentCaptor.forClass(Person.class);
        verify(personRepository,times(1)).save(captor.capture());

        Person deletedPerson = captor.getValue();
        assertThat(deletedPerson.getIsActive()).isFalse();
        assertThat(deletedPerson.getDeletedOn()).isNotNull();
    }

    @Test
    @DisplayName("Must Thrown Exception When Delete Person")
    void deletePersonError(){
        PersonNotFoundException exception = assertThrows(PersonNotFoundException.class,()->personService.deletePerson(person.getId()));

        assertEquals("Person with Id: " + person.getId() + " Not Found Or Deleted, Please Try Again!",exception.getMessage());
        verify(personRepository,times(1)).findById(person.getId());
    }

}
