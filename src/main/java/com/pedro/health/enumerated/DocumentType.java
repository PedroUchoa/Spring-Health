package com.pedro.health.enumerated;

public enum DocumentType {
    CPF("cpf"),
    RG("rg"),
    CNH("cnh");

    private final String documentType;

    DocumentType(String documentType){
        this.documentType = documentType;
    }

    String getDocumentType(){
        return documentType;
    }

}
