package com.bazigar.bulandawaaz.hashtags;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class HashTag {

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String hashTag;
    private Long postsCount = 0L;
    private String posts = "";

    public HashTag(String hashTag, Long postsCount, String posts) {
        this.hashTag = hashTag;
        this.postsCount = postsCount;
        this.posts = posts;
    }

    public HashTag() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHashTag() {
        return hashTag;
    }

    public void setHashTag(String hashTag) {
        this.hashTag = hashTag;
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

    @Override
    public String toString() {
        return "HashTag{" +
                "id=" + id +
                ", hashTag='" + hashTag + '\'' +
                ", postsCount=" + postsCount +
                ", posts='" + posts + '\'' +
                '}';
    }
}
