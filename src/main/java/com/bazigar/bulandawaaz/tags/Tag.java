package com.bazigar.bulandawaaz.tags;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Tag {

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private Long postId;
    private Long userId;
    private Long taggedUserId;
    private String type;
    private String postUrl = "";

    public Tag() {
    }

    public Tag(Long postId, Long userId, Long taggedUserId, String type, String postUrl) {
        this.postId = postId;
        this.userId = userId;
        this.taggedUserId = taggedUserId;
        this.type = type;
        this.postUrl = postUrl;
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

    public Long getTaggedUserId() {
        return taggedUserId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPostUrl() {
        return postUrl;
    }

    public void setPostUrl(String postUrl) {
        this.postUrl = postUrl;
    }

    public void setTaggedUserId(Long taggedUserId) {
        this.taggedUserId = taggedUserId;
    }



    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", postId=" + postId +
                ", userId=" + userId +
                ", taggedUserId=" + taggedUserId +
                ", type=" + type +
                ", postUrl=" + postUrl +
                '}';
    }
}
