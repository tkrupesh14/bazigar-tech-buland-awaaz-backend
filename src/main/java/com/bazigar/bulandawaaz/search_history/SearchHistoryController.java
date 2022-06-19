package com.bazigar.bulandawaaz.search_history;

import com.bazigar.bulandawaaz.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/my-post-api/index.php/api/User")
public class SearchHistoryController {

    @Autowired
    private SearchHistoryService searchHistoryService;

    @PostMapping("/add_to_searchHistory")
    public Response addToSearchHistory(String searchType, Long searchId, Long userId) {
        return searchHistoryService.addToSearchHistory(searchType, searchId, userId);
    }

    @PostMapping("/fetch_searchHistory")
        public Response getSearchHistory(String searchType) {
            return searchHistoryService.fetchSearchHistory(searchType);
    }

}
