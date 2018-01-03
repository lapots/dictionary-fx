package com.lapots.breed.dictionary.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "alphabet")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Alphabet {
    @EmbeddedId
    private AlphabetLetter letter;
    private String pronounce;
    private String description;

    @Embeddable
    @Data
    public static class AlphabetLetter {
        private String letter;
        private String languageId;
    }
}
