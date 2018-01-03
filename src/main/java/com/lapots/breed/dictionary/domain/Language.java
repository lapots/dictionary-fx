package com.lapots.breed.dictionary.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "languages")
@Data
public class Language {
    @Id
    @GenericGenerator(name = "name_based_id",
            strategy = "com.lapots.breed.dictionary.repository.controller.NameBasedGenerator")
    @GeneratedValue(generator = "name_based_id")
    private String id;
    @Column(nullable = false)
    private String name;

    @Override
    public String toString() {
        return name;
    }
}
