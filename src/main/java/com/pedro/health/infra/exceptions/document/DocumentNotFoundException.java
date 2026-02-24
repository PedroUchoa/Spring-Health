package com.pedro.health.infra.exceptions.document;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Document Not Found Or Disabled")
public class DocumentNotFoundException extends RuntimeException{

    public DocumentNotFoundException(String id){
        super("Document with Id: " + id + " Not Found Or Deleted, Please Try Again!");
    }
}
