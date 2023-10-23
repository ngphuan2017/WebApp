/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.annp.repository.impl;

import com.annp.pojo.Report;
import com.annp.pojo.Status;
import com.annp.repository.ReportRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author phuan
 */
@Repository
@Transactional
public class ReportRepositoryImpl implements ReportRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Report> getReports() {
        try {
            Session s = factory.getObject().getCurrentSession();
            Query q = s.createQuery("From Report");
            return q.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public List<Report> getReport(Map<String, String> params, int start, int limit) {
        try {
            Session s = factory.getObject().getCurrentSession();
            CriteriaBuilder b = s.getCriteriaBuilder();
            CriteriaQuery<Report> q = b.createQuery(Report.class);
            Root root = q.from(Report.class);
            q.select(root);
            q.orderBy(b.desc(root.get("reportCount")));
            Query query = s.createQuery(q);
            if (start > 0 && limit > 0) {
                query.setFirstResult(start - 1); // Vị trí bắt đầu
                query.setMaxResults(limit); // Số lượng kết quả trả về
            }
            return query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Report getReportById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(Report.class, id);
    }

    @Override
    public boolean deleteReport(int id) {
        Report r = this.getReportById(id);
        Session s = this.factory.getObject().getCurrentSession();
        try {
            s.delete(r);
            return true;
        } catch (HibernateException ex) {
            return false;
        }
    }

    @Override
    public List<Report> getReportByStatus(Status status) {
        try {
            Session session = this.factory.getObject().getCurrentSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Report> query = builder.createQuery(Report.class);
            Root root = query.from(Report.class);
            query = query.select(root);

            Predicate p = builder.equal(root.get("reportstatus"), status);
            query = query.where(p);
            query = query.orderBy(builder.desc(root.get("id")));

            Query q = session.createQuery(query);

            List<Report> report = q.getResultList();
            return report != null && !report.isEmpty() ? report : new ArrayList<>();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean updateReport(Report report) {
        Session session = this.factory.getObject().getCurrentSession();
        try {
            session.update(report);
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }
    }

}
