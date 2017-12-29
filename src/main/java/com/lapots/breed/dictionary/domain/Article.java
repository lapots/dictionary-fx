package com.lapots.breed.dictionary.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "articles")
@Data
public class Article {
    @Id
    @GeneratedValue
    private String id;
    @Column(nullable = false)
    private String article;
    private String pronounce;
    private String description;
}
