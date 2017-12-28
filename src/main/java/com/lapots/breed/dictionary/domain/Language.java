package com.lapots.breed.dictionary.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "languages")
@Data
public class Language {
    @Id
    private String id;
    @Column(nullable = false)
    private String name;

    @Override
    public String toString() {
        return name;
    }
}
