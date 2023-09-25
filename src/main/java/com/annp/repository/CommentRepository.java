package com.annp.repository;

import com.annp.pojo.Comment;
import com.annp.pojo.Report;

import java.util.List;

public interface CommentRepository {
    List<Comment> getCommentsByProductId(int id, int start, int limit);
    Comment getCommentById(int id);
    Comment addComment(Comment c);
    boolean updateComment(Comment c);
    Report getReportByCommentId(int id);
    boolean addOrUpdateReportComment(Report r);
    boolean deleteComment(int id);
}
