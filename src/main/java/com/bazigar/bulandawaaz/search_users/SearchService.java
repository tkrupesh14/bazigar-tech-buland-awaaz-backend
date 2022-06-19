package com.bazigar.bulandawaaz.search_users;

import com.bazigar.bulandawaaz.User.User;
import com.bazigar.bulandawaaz.User.UserRepository;
import com.bazigar.bulandawaaz.hashtags.HashTag;
import com.bazigar.bulandawaaz.hashtags.HashTagRepository;
import com.bazigar.bulandawaaz.util.PagedObject;
import com.bazigar.bulandawaaz.util.Response;
import com.bazigar.bulandawaaz.util.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SearchService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HashTagRepository hashTagRepository;

    public Response searchUser(String query, Integer pageNo) {
//        List<User> users = userRepository.findAll();
//        ArrayList<String> userNames = new ArrayList<>();
//        for (User i: users) {
//            userNames.add(i.getUserName());
//        }
//        HashSet<String> fullNames = new HashSet<>();
//        for (User i: users) {
//            fullNames.add(i.getFullName());
//        }
//        HashSet<SearchResult> matchingNames = new HashSet<>();
//        Pattern p = Pattern.compile("["+query+"]");
//        ArrayList<String> addedNames = new ArrayList<>();
//        for (String i: userNames) {
//            if (p.matcher(i).find()) {
//                System.out.println(i);
//                Optional<User> user = userRepository.findUserByUserName(i);
//                if (user.isPresent()) {
//                    matchingNames.add(new SearchResult(
//                            user.get().getId().toString(),
//                            user.get().getUserName(),
//                            user.get().getFullName(),
//                            user.get().getProfileUrl()
//                    ));
//                    addedNames.add(i);
//                }
//            }
//        }
//
//        for (String i: fullNames) {
//            if (p.matcher(i).find()) {
//                List<User> userByFullName = userRepository.findUserByFullName(i);
//                for (User rm: userByFullName) {
//                    if (!addedNames.contains(rm.getUserName())) {
//                        matchingNames.add(new SearchResult(
//                                rm.getId().toString(),
//                                rm.getUserName(),
//                                rm.getFullName(),
//                                rm.getProfileUrl()
//                        ));
//                    }
//                }
//            }
//        }
        Pageable paged = PageRequest.of(pageNo, 25);
        Page<User> matchingUsersPage = userRepository.findUsersByUserNameRegexOrFullNameRegex("%"+query+"%", paged);
        List<User> matchingUsers = matchingUsersPage.toList();
        ArrayList<SearchResult> matchingNames = new ArrayList<>();
        for (User i: matchingUsers) {
            matchingNames.add(
                    new SearchResult(
                        i.getId().toString(),
                        i.getUserName(),
                        i.getFullName(),
                        i.getProfileUrl()
                    )
            );
        }
        Collections.shuffle(matchingNames);
        return new Response(
                matchingNames.size()+" results obtained!",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()
                ),
                new PagedObject(
                        matchingNames.toArray(),
                        pageNo,
                        matchingUsersPage.getTotalPages()
                )
        );
    }

    public Response searchTags(String hashTag, Integer pageNo) {
        if (hashTag == null) {
            return new Response(
                    "hashTag is required for searching!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        if (hashTag.charAt(0) != '#') {
//            List<HashTag> tags = hashTagRepository.findAll();
//            ArrayList<TagSearchResult> tagSearchResults = new ArrayList<>();
//            for (HashTag tag: tags) {
//                if (tag.getHashTag().contains(hashTag)) {
//                    tagSearchResults.add(new TagSearchResult(
//                            tag.getHashTag(),
//                            tag.getPostsCount()
//                    ));
//                }
//            }
            Pageable paged = PageRequest.of(pageNo, 25);
            Page<HashTag> hashTagsPage = hashTagRepository.findHashTagsByPage(hashTag, paged);
            List<HashTag> tagSearchResults = hashTagsPage.toList();
            return new Response(
                    hashTagsPage.getTotalElements()+" results obtained!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    ),
                    new PagedObject(
                            tagSearchResults.toArray(),
                            pageNo,
                            hashTagsPage.getTotalPages()
                    )
            );
        }
        String tagString = hashTag.substring(1);
        Pageable paged = PageRequest.of(pageNo, 25);
        Page<HashTag> hashTagsPage = hashTagRepository.findHashTagsByPage(tagString, paged);
        List<HashTag> tagSearchResults = hashTagsPage.toList();
        return new Response(
                tagSearchResults.size()+" results obtained!",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()
                ),
                new PagedObject(
                        tagSearchResults.toArray(),
                        pageNo,
                        hashTagsPage.getTotalPages()
                )
        );
    }
}
