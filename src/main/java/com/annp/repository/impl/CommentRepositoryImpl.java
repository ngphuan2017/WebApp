package com.annp.repository.impl;

import com.annp.pojo.Comment;
import com.annp.pojo.Report;
import com.annp.repository.CommentRepository;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
@Transactional
public class CommentRepositoryImpl implements CommentRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Comment> getCommentsByProductId(int id, int start, int limit) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Comment> q = b.createQuery(Comment.class);
        Root root = q.from(Comment.class);
        q.select(root);

        q.where(b.equal(root.get("productid"), id));
        q.orderBy(b.desc(root.get("createdDate")));
        Query query = s.createQuery(q);
        if (start > 0 && limit > 0) {
            query.setFirstResult(start - 1); // Vị trí bắt đầu
            query.setMaxResults(limit); // Số lượng kết quả trả về
        }
        return query.getResultList();

    }

    @Override
    public Comment addComment(Comment c) {
        Session s = this.factory.getObject().getCurrentSession();
        try {
            s.save(c);
            return c;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Comment getCommentById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(Comment.class, id);
    }

    @Override
    public boolean updateComment(Comment c) {
        Session s = this.factory.getObject().getCurrentSession();
        try {
            s.update(c);
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public Report getReportByCommentId(int id) {
        try {
            Session s = factory.getObject().getCurrentSession();
            CriteriaBuilder b = s.getCriteriaBuilder();
            CriteriaQuery<Report> q = b.createQuery(Report.class);
            Root root = q.from(Report.class);
            q.select(root);
            Comment c = getCommentById(id);
            q.where(b.equal(root.get("commentid"), c));
            Query query = s.createQuery(q);
            return (Report) query.getSingleResult();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean addOrUpdateReportComment(Report r) {
        Session session = this.factory.getObject().getCurrentSession();
        try {
            if (r.getId() != 0) {
                session.update(r);
            } else {
                session.save(r);
            }
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteComment(int id) {
        Comment c = this.getCommentById(id);
        Session s = this.factory.getObject().getCurrentSession();
        try {
            s.delete(c);
            return true;
        } catch (HibernateException ex) {
            return false;
        }
    }
}
