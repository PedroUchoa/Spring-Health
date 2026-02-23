CREATE TABLE person(
    id VARCHAR(255) NOT NULL PRIMARY KEY UNIQUE,
    name VARCHAR(50) NOT NULL,
    phone VARCHAR(11) NOT NULL,
    birth_date DATE NOT NULL,
    is_active BOOLEAN NOT NULL,
    deleted_on DATETIME,
    created_on DATETIME NOT NULL,
    updated_on DATETIME
)