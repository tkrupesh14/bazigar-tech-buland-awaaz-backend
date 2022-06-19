package com.bazigar.bulandawaaz.mentions;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Mention {

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private Long userId;
    private Long mentionedUserId;
    private Long postId;
    private Long createdAt;
    private String type;

    public Mention() {
    }

    public Mention(Long userId, Long mentionedUserId, Long postId, Long createdAt, String type) {
        this.userId = userId;
        this.mentionedUserId = mentionedUserId;
        this.postId = postId;
        this.createdAt = createdAt;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getMentionedUserId() {
        return mentionedUserId;
    }

    public void setMentionedUserId(Long mentionedUserId) {
        this.mentionedUserId = mentionedUserId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
