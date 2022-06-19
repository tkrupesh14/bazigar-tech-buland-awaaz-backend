package com.bazigar.bulandawaaz.category;


import com.bazigar.bulandawaaz.uploads.posts.PostRepository;
import com.bazigar.bulandawaaz.util.Response;
import com.bazigar.bulandawaaz.util.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SearchCategoryService {

    @Autowired
    private PostRepository postRepository;


    @Autowired
    private SearchCategoryRepository categoryRepository;

//    @Value("${bazigar.password}")
//    private String bazigarPassword;

    public Response addToCategory(String category, Long postId) {
        if (category == null||postId==null) {
            return new Response(
                    "Category name and postId are required!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }


        Optional<SearchCategory> category1 = categoryRepository.findCategoryByName(category);
        if (category1.isPresent()) {
            Long currentPostsCount = category1.get().getPostsCount();
            String postsForTag = category1.get().getPosts();
            String[] postsList = postsForTag.split(", ");
            ArrayList<String> mutablePostsList = new ArrayList<>(Arrays.asList(postsList));
            if (mutablePostsList.contains("post-" + postId)) {
                return new Response(
                        "PostId has already been added to this category!",
                        new ResponseStatus(
                                HttpStatus.CONFLICT.value(),
                                HttpStatus.CONFLICT.getReasonPhrase()
                        )
                );
            }
            mutablePostsList.add("post-" + postId);
            String updatedPosts = String.join(", ", mutablePostsList);
            categoryRepository.updateCategory(updatedPosts, category1.get().getPostsCount() + 1L, category1.get().getId());
            return new Response(
                    "category Updated!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        return new Response(
                "No Category Found!",
                new ResponseStatus(
                        HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST.getReasonPhrase()
                )
        );
    }

    public Response getCategory() {
        List<SearchCategory> categoryList = categoryRepository.findAllCategory();
        return new Response(
                "Found " + categoryList.size() + " categories!",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()
                ),
                categoryList
        );

        }




    public Response createCategory(String category) {
//        if (category == null) {
//            return new Response(
//                    "Category name is  required!",
//                    new ResponseStatus(
//                            HttpStatus.BAD_REQUEST.value(),
//                            HttpStatus.BAD_REQUEST.getReasonPhrase()
//                    )
//            );
//        }


        Optional<SearchCategory> category1 = categoryRepository.findCategoryByName(category);
        if (category1.isPresent()) {
            return new Response(
                    "Category already exists!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        categoryRepository.save(new SearchCategory(category,0L));
        return new Response(
                "Category created!",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()
                )
        );
    }
}

