package com.pedro.health.repositories;

import com.pedro.health.domains.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document,String> {
}
