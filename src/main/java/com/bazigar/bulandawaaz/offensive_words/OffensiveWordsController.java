package com.bazigar.bulandawaaz.offensive_words;

import com.bazigar.bulandawaaz.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/my-post-api/index.php/api/User")
public class OffensiveWordsController {

    @Autowired
    private OffensiveWordsService offensiveWordsService;

    @PostMapping("/add_offensive_word")
    public Response addOffensiveWord(String word) {
        return offensiveWordsService.addOffensiveWord(word);
    }

}
