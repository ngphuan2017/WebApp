package com.annp.service;

import com.annp.pojo.Comment;
import com.annp.pojo.Report;

import java.util.List;

public interface CommentService {
    List<Comment> getCommentsByProductId(int id);
    Comment addComment(Comment c);
    Comment getCommentById(int id);
    boolean updateComment(Comment c);
    Report getReportByCommentId(int id);
    boolean addOrUpdateReportComment(Report r);
    boolean deleteComment(int id);
}
