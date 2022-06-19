package com.bazigar.bulandawaaz.comment_replies.post;

import com.bazigar.bulandawaaz.User.User;
import com.bazigar.bulandawaaz.comments.posts.PostComments;

import javax.persistence.*;

@Entity
public class PostCommentReply {

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    private String reply;
    private Long createdAt;
    @ManyToOne
    @JoinColumn(name = "comment_id", referencedColumnName = "id")
    private PostComments postComments;

    public PostCommentReply() {
    }

    public PostCommentReply(User user, String reply, Long createdAt, PostComments postComments) {
        this.user = user;
        this.reply = reply;
        this.createdAt = createdAt;
        this.postComments = postComments;
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

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public PostComments getPostComments() {
        return postComments;
    }

    public void setPostComments(PostComments postComments) {
        this.postComments = postComments;
    }

    @Override
    public String toString() {
        return "PostCommentReply{" +
                "id=" + id +
                ", user=" + user +
                ", reply='" + reply + '\'' +
                ", createdAt=" + createdAt +
                ", postComments=" + postComments +
                '}';
    }
}
