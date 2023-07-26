package com.annp.service.impl;

import com.annp.pojo.Comment;
import com.annp.repository.CommentRepository;
import com.annp.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepo;

    @Override
    public List<Comment> getComments() {
        return this.commentRepo.getComments();
    }

    @Override
    public Comment addComment(Comment c) {
        return this.commentRepo.addComment(c);
    }
}
