package com.pedro.health.repositories;

import com.pedro.health.domains.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document,String> {
    List<Document> findAllByPersonId(String personId);
}
