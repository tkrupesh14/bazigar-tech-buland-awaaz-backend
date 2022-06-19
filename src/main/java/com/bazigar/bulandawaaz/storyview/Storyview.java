package com.bazigar.bulandawaaz.storyview;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Storyview {

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private Long storyId;
    private Long userId;
    private Long viewedAt;

    public Storyview() {
    }

    public Storyview(Long storyId, Long userId) {
        this.storyId = storyId;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStoryId() {
        return storyId;
    }

    public void setStoryId(Long storyId) {
        this.storyId = storyId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getViewedAt() {
        return viewedAt;
    }

    public void setViewedAt(Long viewedAt) {
        this.viewedAt = viewedAt;
    }
}
