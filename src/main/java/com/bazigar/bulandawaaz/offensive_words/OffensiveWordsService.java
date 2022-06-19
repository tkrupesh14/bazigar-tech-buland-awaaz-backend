package com.bazigar.bulandawaaz.offensive_words;

import com.bazigar.bulandawaaz.util.Response;
import com.bazigar.bulandawaaz.util.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OffensiveWordsService {

    @Autowired
    private OffensiveWordsRepository offensiveWordsRepository;

    public Response addOffensiveWord(String word) {
        if (word == null) {
            return new Response(
                    "word is required!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        if (word.isEmpty()) {
            return new Response(
                    "Offensive word to be added cannot be empty!",
                    new ResponseStatus(
                            HttpStatus.NO_CONTENT.value(),
                            HttpStatus.NO_CONTENT.getReasonPhrase()
                    )
            );
        }
        Optional<OffensiveWords> offensiveWord = offensiveWordsRepository.findByWord(word);
        if (offensiveWord.isPresent()) {
            return new Response(
                    "This word has already been added to the offensive words list!",
                    new ResponseStatus(
                            HttpStatus.CONFLICT.value(),
                            HttpStatus.CONFLICT.getReasonPhrase()
                    )
            );
        }
        OffensiveWords offensiveWords = new OffensiveWords(
                word
        );
        offensiveWordsRepository.save(offensiveWords);
        return new Response(
                "Word successfully added to offensive words list!",
                new ResponseStatus(
                        HttpStatus.CONFLICT.value(),
                        HttpStatus.CONFLICT.getReasonPhrase()
                )
        );
    }

}
