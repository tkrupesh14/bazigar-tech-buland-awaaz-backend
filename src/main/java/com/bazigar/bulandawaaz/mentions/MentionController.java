package com.bazigar.bulandawaaz.mentions;

import com.bazigar.bulandawaaz.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/my-post-api/index.php/api/User")
public class MentionController {

    @Autowired
    private MentionService mentionService;

    @PostMapping("/mention")
    public Response createMention(MentionHelper helper) {
        return mentionService.addMention(helper);
    }






}
