package com.pedro.health.service;

import com.pedro.health.domains.Person;
import com.pedro.health.dtos.person.CreatePersonDto;
import com.pedro.health.dtos.person.ReturnPersonDto;
import com.pedro.health.dtos.person.UpdatePersonDto;
import com.pedro.health.infra.exceptions.person.PersonNotFoundException;
import com.pedro.health.repositories.PersonRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Transactional
    @CacheEvict(value = {"person","document"}, allEntries = true)
    public ReturnPersonDto createPerson(CreatePersonDto createPersonDto){
        Person person = new Person(createPersonDto);
        Person returnPerson = personRepository.save(person);
        return new ReturnPersonDto(returnPerson);
    }

    @Cacheable(value = "person")
    public List<ReturnPersonDto> listAllPersons(){
        return personRepository.findAll().stream().map(ReturnPersonDto::new).toList();
    }

    @Transactional
    @CacheEvict(value = {"person","document"}, allEntries = true)
    public ReturnPersonDto updatePerson(String personId,UpdatePersonDto updatePerson){
        Person person = personRepository.findById(personId).orElseThrow(()->new PersonNotFoundException(personId));
        person.update(updatePerson);
        personRepository.save(person);
        return new ReturnPersonDto(person);
    }

    @Transactional
    @CacheEvict(value = {"person","document"}, allEntries = true)
    public void deletePerson(String personId){
        Person person = personRepository.findById(personId).orElseThrow(()->new PersonNotFoundException(personId));
        person.disable();
        personRepository.save(person);
    }

}
