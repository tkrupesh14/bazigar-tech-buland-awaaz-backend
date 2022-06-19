package com.bazigar.bulandawaaz.offensive_words;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OffensiveWordsRepository extends JpaRepository<OffensiveWords, Long> {

    @Query("select s from OffensiveWords s where s.word = ?1")
    public Optional<OffensiveWords> findByWord(String word);

}
