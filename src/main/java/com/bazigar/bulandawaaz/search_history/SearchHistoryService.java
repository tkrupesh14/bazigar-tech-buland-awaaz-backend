package com.bazigar.bulandawaaz.search_history;

import com.bazigar.bulandawaaz.search_users.SearchResult;
import com.bazigar.bulandawaaz.search_users.TagSearchResult;
import com.bazigar.bulandawaaz.User.User;
import com.bazigar.bulandawaaz.User.UserRepository;
import com.bazigar.bulandawaaz.hashtags.HashTag;
import com.bazigar.bulandawaaz.hashtags.HashTagRepository;
import com.bazigar.bulandawaaz.util.Response;
import com.bazigar.bulandawaaz.util.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SearchHistoryService {

    @Autowired
    private SearchHistoryRepository searchHistoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HashTagRepository hashTagRepository;

    public Response addToSearchHistory(String searchType, Long searchId, Long userId) {
        if (searchType == null || searchId == null || userId == null) {
            return new Response(
                    "searchType, searchId and userId are required!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return new Response(
                    "No user found with the given userId!",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }
        Timestamp timer = Timestamp.from(Instant.now());
        if (searchType.equals("user")) {
            Optional<User> user1 = userRepository.findById(searchId);
            if (user1.isEmpty()) {
                return new Response(
                        "No user found with the given searchId!",
                        new ResponseStatus(
                                HttpStatus.NOT_FOUND.value(),
                                HttpStatus.NOT_FOUND.getReasonPhrase()
                        )
                );
            }
            SearchHistory item = new SearchHistory(
                    "user-"+searchId,
                    userId,
                    0L
            );
            item.setCreatedAt(timer.getTime());
            searchHistoryRepository.save(item);
        }
        else if (searchType.equals("hashtag")) {
            Optional<HashTag> tagger = hashTagRepository.findById(searchId);
            if (tagger.isEmpty()) {
                return new Response(
                        "No hashtag found with the given searchId!",
                        new ResponseStatus(
                                HttpStatus.NOT_FOUND.value(),
                                HttpStatus.NOT_FOUND.getReasonPhrase()
                        )
                );
            }
            SearchHistory item = new SearchHistory(
                    "hashtag-"+searchId,
                    userId,
                    0L
            );
            item.setCreatedAt(timer.getTime());
            searchHistoryRepository.save(item);
        }
        else {
            return new Response(
                    "searchType can only be user or hashtag",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        return new Response(
                "Item successfully saved to searchHistory!",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()
                )
        );
    }

    public Response fetchSearchHistory(String searchType) {
        if (searchType == null) {
            return new Response(
                    "searchType is required and cannot be null",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        if (searchType.equals("user")) {
            List<SearchHistory> searchHistoryList = searchHistoryRepository.findAll();
            ArrayList<SearchResult> users = new ArrayList<>();
            for (SearchHistory i: searchHistoryList) {
                if (i.getSearchInfo().contains("user")) {
                      String[] searchInfoSplitted = i.getSearchInfo().split("-");
                      Optional<User> user = userRepository.findById(Long.parseLong(searchInfoSplitted[1]));
                        user.ifPresent(value -> users.add(new SearchResult(
                                value.getId().toString(),
                                value.getUserName(),
                                value.getFullName(),
                                value.getProfileUrl()
                        )));
                }
            }
            return new Response(
                    users.size()+" items in searchHistory",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    ),
                    users
            );
        }
        else if (searchType.equals("hashtag")) {
            List<SearchHistory> searchHistoryList = searchHistoryRepository.findAll();
            ArrayList<TagSearchResult> tagList = new ArrayList<>();
            for (SearchHistory i: searchHistoryList) {
                if (i.getSearchInfo().contains("hashtag")) {
                    String[] searchInfoSplitted = i.getSearchInfo().split("-");
                    Optional<HashTag> tag = hashTagRepository.findById(Long.parseLong(searchInfoSplitted[1]));
                    tag.ifPresent(hashTag -> tagList.add(new TagSearchResult(
                            hashTag.getHashTag(),
                            hashTag.getPostsCount()
                    )));
                }
            }
            return new Response(
                    tagList.size()+" items in searchHistory",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    ),
                    tagList
            );
        }
        else {
            return new Response(
                    "searchType can only be user or hashtag!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
    }
}
