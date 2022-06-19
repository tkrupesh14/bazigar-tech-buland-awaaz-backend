package com.bazigar.bulandawaaz.hide_post;

import javax.persistence.*;

@Entity
public class HidePost {
    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private Long postId;
    private Long userId;


    public HidePost() {
    }

    public HidePost(Long postId, Long userId) {
        this.postId = postId;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}