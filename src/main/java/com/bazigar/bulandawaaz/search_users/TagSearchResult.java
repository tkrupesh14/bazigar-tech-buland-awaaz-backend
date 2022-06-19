package com.bazigar.bulandawaaz.search_users;

public class TagSearchResult {

    private String hashTag;
    private Long postsCount;

    public TagSearchResult() {
    }

    public TagSearchResult(String hashTag, Long postsCount) {
        this.hashTag = hashTag;
        this.postsCount = postsCount;
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
}
