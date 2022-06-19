package com.bazigar.bulandawaaz.offensive_words;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class OffensiveWords {

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String word;

    public OffensiveWords() {
    }

    public OffensiveWords(String word) {
        this.word = word;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Override
    public String toString() {
        return "OffensiveWords{" +
                "id=" + id +
                ", word=" + word +
                '}';
    }
}
