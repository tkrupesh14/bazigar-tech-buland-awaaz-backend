package com.bazigar.bulandawaaz.category;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Entity
public class SearchCategory {
    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String category;
    private Long postsCount = 0L;
    private String posts = "";

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Long getPostsCount() {
        return postsCount;
    }

    public void setPostsCount(Long postsCount) {
        this.postsCount = postsCount;
    }

    public String getPosts() {
        return posts;
    }

    public void setPosts(String posts) {
        this.posts = posts;
    }

    public SearchCategory() {
    }

    public SearchCategory( String category, Long postsCount, String posts) {
        this.category = category;
        this.postsCount = postsCount;
        this.posts = posts;
    }


    public SearchCategory( String category) {
        this.category = category;
    }
    public SearchCategory( String category,Long postsCount) {
        this.category = category;
        this.postsCount=postsCount;
    }

    public SearchCategory(String category, List<Long>postId) {
        this.category = category;
        ArrayList<String> mutablePostsList = new ArrayList<>();
        for (Long id : postId) {
            mutablePostsList.add("post-" + id);
        }
        String updatedPosts = String.join(", ", mutablePostsList);
        this.posts =updatedPosts;
    }
}
