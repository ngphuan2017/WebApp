package com.annp.service;

import com.annp.pojo.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> getComments();
    Comment addComment(Comment c);
}
