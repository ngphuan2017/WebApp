package com.annp.service.impl;

import com.annp.pojo.Comment;
import com.annp.pojo.Report;
import com.annp.repository.CommentRepository;
import com.annp.service.CommentService;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepo;

    @Override
    public List<Comment> getCommentsByProductId(int id, int start, int limit) {
        return this.commentRepo.getCommentsByProductId(id, start, limit);
    }

    @Override
    public Comment addComment(Comment c) {
        c.setLikeCount(0);
        c.setDislike(0);
        c.setCreatedDate(new Date());
        return this.commentRepo.addComment(c);
    }

    @Override
    public Comment getCommentById(int id) {
        return this.commentRepo.getCommentById(id);
    }

    @Override
    public boolean updateComment(Comment c) {
        return this.commentRepo.updateComment(c);
    }

    @Override
    public boolean addOrUpdateReportComment(Report r) {
        return this.commentRepo.addOrUpdateReportComment(r);
    }

    @Override
    public Report getReportByCommentId(int id) {
        return this.commentRepo.getReportByCommentId(id);
    }

    @Override
    public boolean deleteComment(int id) {
        return this.commentRepo.deleteComment(id);
    }
}
