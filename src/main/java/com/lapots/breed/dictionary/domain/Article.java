package com.lapots.breed.dictionary.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "articles")
@Data
public class Article {
    @Id
    private String id;
    @Column(nullable = false)
    private String article;
    private String pronounce;
    private String description;
}
