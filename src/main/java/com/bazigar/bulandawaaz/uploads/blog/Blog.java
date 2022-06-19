package com.bazigar.bulandawaaz.uploads.blog;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Blog {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private Long userId;
    private String content = "";
    private String location = "";

    public Blog(Long userId, String content, String location) {
        this.userId = userId;
        this.content = content;
        this.location = location;
    }

    public Blog() {
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
