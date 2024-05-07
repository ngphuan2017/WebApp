package com.annp.service;

import com.annp.pojo.Comment;
import com.annp.pojo.Report;

import java.util.List;
import java.util.Map;

public interface CommentService {
    List<Comment> getCommentsByProductId(int id, int start, int limit);
    List<Comment> getComments(Map<String, String> params, int start, int limit);
    Comment addComment(Comment c);
    Comment getCommentById(int id);
    boolean updateComment(Comment c);
    Report getReportByCommentId(int id);
    boolean addOrUpdateReportComment(Report r);
    boolean deleteComment(int id);
}
