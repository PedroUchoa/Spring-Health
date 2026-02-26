package com.pedro.health.domains;

import com.pedro.health.dtos.person.CreatePersonDto;
import com.pedro.health.dtos.person.UpdatePersonDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "Person")
@Table(name = "person")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@SQLRestriction(value = "is_active = true")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String phone;
    private LocalDate birthDate;
    @CreationTimestamp
    private LocalDateTime createdOn;
    @UpdateTimestamp
    private LocalDateTime updatedOn;
    private LocalDateTime deletedOn;
    private Boolean isActive = true;
    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private List<Document> documents;

    public Person(CreatePersonDto createPersonDto) {
        this.name = createPersonDto.name();
        this.phone = createPersonDto.phone();
        this.birthDate = createPersonDto.birthDate();
        this.documents = createPersonDto.documents().stream().map(Document::new).toList();
        getDocuments().forEach(document -> document.setPerson(this));
    }


    public void disable(){
        this.setIsActive(false);
        this.setDeletedOn(LocalDateTime.now());
        this.getDocuments().forEach(Document::disable);
    }

    public void update(UpdatePersonDto updatePerson) {
        this.setName(updatePerson.name());
        this.setPhone(updatePerson.phone());
        this.setBirthDate(updatePerson.birthDate());
    }
}
