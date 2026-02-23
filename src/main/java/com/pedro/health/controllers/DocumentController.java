package com.pedro.health.controllers;

import com.pedro.health.dtos.document.CreateDocumentDto;
import com.pedro.health.dtos.document.ReturnDocumentDto;
import com.pedro.health.service.DocumentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/document")
public class DocumentController {

    @Autowired
    private DocumentService documentService;


    @GetMapping("/{personId}")
    public ResponseEntity<List<ReturnDocumentDto>> returnAllDocumentsByPersonId(@PathVariable String personId){
        List<ReturnDocumentDto> returnDocumentDto = documentService.returnAllDocumentsByPersonId(personId);
        return ResponseEntity.ok(returnDocumentDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReturnDocumentDto> updateDocumentDto(@PathVariable String id, @Valid @RequestBody CreateDocumentDto updateDocument){
        ReturnDocumentDto returnDocumentDto = documentService.updateDocument(id,updateDocument);
        return ResponseEntity.ok(returnDocumentDto);
    }



}
