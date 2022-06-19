package com.bazigar.bulandawaaz.comment_replies.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostCommentReplyRepository extends
        JpaRepository<PostCommentReply, Long> {


}
