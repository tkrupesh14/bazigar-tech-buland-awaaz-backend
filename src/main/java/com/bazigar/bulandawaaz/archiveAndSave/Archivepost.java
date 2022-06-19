package com.bazigar.bulandawaaz.archiveAndSave;

import com.bazigar.bulandawaaz.User.User;

import javax.persistence.*;

@Entity
public class Archivepost {

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    private Integer type;
    private Long post_id;
    private String postUrl = "";
    private Long createdAt;

    public Archivepost() {
    }

    public Archivepost(User user, Integer type, Long post_id, String postUrl, Long createdAt) {
        this.user = user;
        this.type = type;
        this.post_id = post_id;
        this.postUrl = postUrl;
        this.createdAt = createdAt;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public String getPostUrl() {
        return postUrl;
    }

    public void setPostUrl(String postUrl) {
        this.postUrl = postUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getPost_id() {
        return post_id;
    }

    public void setPost_id(Long post_id) {
        this.post_id = post_id;
    }

    @Override
    public String toString() {
        return "Archivepost{" +
                "id=" + id +
                ", user=" + user +
                ", type=" + type +
                ", post_id=" + post_id +
                '}';
    }
}
