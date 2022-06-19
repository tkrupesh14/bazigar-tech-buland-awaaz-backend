package com.bazigar.bulandawaaz.search_users;

import com.bazigar.bulandawaaz.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/buland-awaaz/api/User")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @PostMapping("/search")
    public Response searchForUsers(String query, Integer pageNo) {
        return searchService.searchUser(query, pageNo);
    }

    @PostMapping("/search_tags")
    public Response searchTags(String hashTag, Integer pageNo) {
        return searchService.searchTags(hashTag, pageNo);
    }

}
