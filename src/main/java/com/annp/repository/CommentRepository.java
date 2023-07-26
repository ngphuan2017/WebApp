package com.annp.repository;

import com.annp.pojo.Comment;

import java.util.List;

public interface CommentRepository {
    List<Comment> getComments();
    Comment addComment(Comment c);
}
