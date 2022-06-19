package com.bazigar.bulandawaaz.search_history;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SearchHistory {

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String searchInfo;
    private Long userId;
    private Long createdAt;

    public SearchHistory() {
    }

    public SearchHistory(String searchInfo, Long userId, Long createdAt) {
        this.searchInfo = searchInfo;
        this.userId = userId;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSearchInfo() {
        return searchInfo;
    }

    public void setSearchInfo(String searchInfo) {
        this.searchInfo = searchInfo;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "SearchHistory{" +
                "id=" + id +
                ", searchInfo='" + searchInfo + '\'' +
                ", userId=" + userId +
                ", createdAt=" + createdAt +
                '}';
    }
}
