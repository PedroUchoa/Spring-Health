package com.pedro.health.controllers;

import com.pedro.health.dtos.person.CreatePersonDto;
import com.pedro.health.dtos.person.ReturnPersonDto;
import com.pedro.health.dtos.person.UpdatePersonDto;
import com.pedro.health.service.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping
    public ResponseEntity<ReturnPersonDto> createPerson(@Valid @RequestBody CreatePersonDto createPersonDto, UriComponentsBuilder uri){
        ReturnPersonDto returnPersonDto = personService.createPerson(createPersonDto);
        URI location = uri.path("/id").buildAndExpand(returnPersonDto.id()).toUri();
        return ResponseEntity.created(location).body(returnPersonDto);
    }

    @GetMapping
    public ResponseEntity<List<ReturnPersonDto>> listAllPersons(){
        List<ReturnPersonDto> personDtoList = personService.listAllPersons();
        return ResponseEntity.ok(personDtoList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReturnPersonDto> updatePerson(@PathVariable String id,@Valid @RequestBody UpdatePersonDto updatePersonDto){
        ReturnPersonDto personDto = personService.updatePerson(id,updatePersonDto);
        return ResponseEntity.ok(personDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable String id){
        personService.deletePerson(id);
        return ResponseEntity.noContent().build();
    }

}
