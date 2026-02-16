package com.pedro.health.service;

import com.pedro.health.domains.Person;
import com.pedro.health.dtos.person.CreatePersonDto;
import com.pedro.health.dtos.person.ReturnPersonDto;
import com.pedro.health.dtos.person.UpdatePersonDto;
import com.pedro.health.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public ReturnPersonDto createPerson(CreatePersonDto createPersonDto){
        Person person = new Person(createPersonDto);
        Person returnPerson = personRepository.save(person);
        return new ReturnPersonDto(returnPerson);
    }

    public List<ReturnPersonDto> listAllPersons(){
        return personRepository.findAll().stream().map(ReturnPersonDto::new).toList();
    }

    public ReturnPersonDto updatePerson(String personId,UpdatePersonDto updatePerson){
        Person person = personRepository.getReferenceById(personId);
        person.update(updatePerson);
        personRepository.save(person);
        return new ReturnPersonDto(person);
    }

    public void deletePerson(String personId){
        Person person = personRepository.getReferenceById(personId);
        person.disable();
        personRepository.save(person);
    }

}
