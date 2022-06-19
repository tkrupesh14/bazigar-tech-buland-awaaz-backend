package com.bazigar.bulandawaaz.comment_likes.post;

import com.bazigar.bulandawaaz.User.User;
import com.bazigar.bulandawaaz.comments.posts.PostComments;

import javax.persistence.*;

@Entity
public class PostCommentLike {

    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @OneToOne
    @JoinColumn(name = "comment_id", referencedColumnName = "id")
    private PostComments postComments;
    private Long likedAt;

    public PostCommentLike() {
    }

    public PostCommentLike(User user, PostComments postComments, Long likedAt) {
        this.user = user;
        this.postComments = postComments;
        this.likedAt = likedAt;
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

    public PostComments getPostComments() {
        return postComments;
    }

    public void setPostComments(PostComments postComments) {
        this.postComments = postComments;
    }

    public Long getLikedAt() {
        return likedAt;
    }

    public void setLikedAt(Long likedAt) {
        this.likedAt = likedAt;
    }

    @Override
    public String toString() {
        return "PostCommentLike{" +
                "id=" + id +
                ", user=" + user +
                ", postComments=" + postComments +
                ", likedAt=" + likedAt +
                '}';
    }
}
