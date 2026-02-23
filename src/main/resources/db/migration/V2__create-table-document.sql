CREATE TABLE document(
    id VARCHAR(255) NOT NULL PRIMARY KEY UNIQUE,
    person_id VARCHAR(255) NOT NULL,
    description VARCHAR(250) NOT NULL,
    document_type VARCHAR(3) NOT NULL,
    is_active BOOLEAN NOT NULL,
    deleted_on DATETIME,
    created_on DATETIME NOT NULL,
    updated_on DATETIME,
    FOREIGN KEY (person_id) REFERENCES person(id)
)