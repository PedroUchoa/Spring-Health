package com.pedro.health.infra.exceptions.person;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Person Not Found Or Disabled")
public class PersonNotFoundException extends RuntimeException{

    public PersonNotFoundException(String id){
        super("Person with Id: " + id + " Not Found Or Deleted, Please Try Again!");
    }
}
