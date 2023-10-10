package com.annp.repository.impl;

import com.annp.pojo.Category;
import com.annp.pojo.CategorySub;
import com.annp.pojo.Orders;
import com.annp.pojo.OrderDetail;
import com.annp.pojo.Product;
import com.annp.repository.StatsRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class StatsRepositoryImpl implements StatsRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Object[]> statsDay() {
        try {
            Session session = this.factory.getObject().getCurrentSession();
            CriteriaBuilder b = session.getCriteriaBuilder();
            CriteriaQuery<Object[]> q = b.createQuery(Object[].class);

            Root rootC = q.from(Category.class);
            Root rootCS = q.from(CategorySub.class);
            Root rootP = q.from(Product.class);
            Root rootOD = q.from(OrderDetail.class);
            Root rootO = q.from(Orders.class);

            List<Predicate> predicates = new ArrayList<>();
            predicates.add(b.equal(rootCS.get("categoryId"), rootC.get("id")));
            predicates.add(b.equal(rootP.get("categorysubId"), rootCS.get("id")));
            predicates.add(b.equal(rootOD.get("productId"), rootP.get("id")));
            predicates.add(b.equal(rootOD.get("orderId"), rootO.get("id")));
            predicates.add(b.equal(b.function("YEAR", Integer.class, rootO.get("createdDate")),
                    b.function("YEAR", Integer.class, b.currentDate())));
            predicates.add(b.equal(b.function("MONTH", Integer.class, rootO.get("createdDate")),
                    b.function("MONTH", Integer.class, b.currentDate())));
            predicates.add(b.equal(b.function("DAY", Integer.class, rootO.get("createdDate")),
                    b.function("DAY", Integer.class, b.currentDate())));
            q.where(predicates.toArray(Predicate[]::new));

            q.multiselect(rootC.get("id"), rootC.get("name"), b.function("DAY",
                    Integer.class, rootO.get("createdDate")),
                    b.sum(b.prod(rootOD.get("price"), rootOD.get("number"))));

            q.groupBy(b.function("DAY", Integer.class, rootO.get("createdDate")));

            Query query = session.createQuery(q);
            List<Object[]> statsDay = query.getResultList();
            return statsDay;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Object[]> statsMonth() {
        try {
            Session session = this.factory.getObject().getCurrentSession();
            CriteriaBuilder b = session.getCriteriaBuilder();
            CriteriaQuery<Object[]> q = b.createQuery(Object[].class);

            Root rootC = q.from(Category.class);
            Root rootCS = q.from(CategorySub.class);
            Root rootP = q.from(Product.class);
            Root rootOD = q.from(OrderDetail.class);
            Root rootO = q.from(Orders.class);

            List<Predicate> predicates = new ArrayList<>();
            predicates.add(b.equal(rootCS.get("categoryId"), rootC.get("id")));
            predicates.add(b.equal(rootP.get("categorysubId"), rootCS.get("id")));
            predicates.add(b.equal(rootOD.get("productId"), rootP.get("id")));
            predicates.add(b.equal(rootOD.get("orderId"), rootO.get("id")));
            predicates.add(b.equal(b.function("YEAR", Integer.class, rootO.get("createdDate")),
                    b.function("YEAR", Integer.class, b.currentDate())));
            predicates.add(b.equal(b.function("MONTH", Integer.class, rootO.get("createdDate")),
                    b.function("MONTH", Integer.class, b.currentDate())));
            q.where(predicates.toArray(Predicate[]::new));

            q.multiselect(b.function("MONTH", Integer.class, rootO.get("createdDate")), 
                    b.countDistinct(rootOD.get("orderId")), 
                    b.sum(rootOD.get("number")), 
                    b.sum(b.prod(rootOD.get("price"), rootOD.get("number"))));
            
            q.groupBy(b.function("MONTH", Integer.class, rootO.get("createdDate")));
            q.orderBy(b.asc(rootO.get("createdDate")));
            Query query = session.createQuery(q);
            List<Object[]> statsMonth = query.getResultList();
            return statsMonth;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Object[]> statsYear() {
        try {
            Session session = this.factory.getObject().getCurrentSession();
            CriteriaBuilder b = session.getCriteriaBuilder();
            CriteriaQuery<Object[]> q = b.createQuery(Object[].class);

            Root rootC = q.from(Category.class);
            Root rootCS = q.from(CategorySub.class);
            Root rootP = q.from(Product.class);
            Root rootOD = q.from(OrderDetail.class);
            Root rootO = q.from(Orders.class);

            List<Predicate> predicates = new ArrayList<>();
            predicates.add(b.equal(rootCS.get("categoryId"), rootC.get("id")));
            predicates.add(b.equal(rootP.get("categorysubId"), rootCS.get("id")));
            predicates.add(b.equal(rootOD.get("productId"), rootP.get("id")));
            predicates.add(b.equal(rootOD.get("orderId"), rootO.get("id")));
            predicates.add(b.equal(b.function("YEAR", Integer.class, rootO.get("createdDate")),
                    b.function("YEAR", Integer.class, b.currentDate())));
            q.where(predicates.toArray(Predicate[]::new));

            q.multiselect(rootC.get("id"), rootC.get("name"), b.function("YEAR",
                    Integer.class, rootO.get("createdDate")),
                    b.sum(b.prod(rootOD.get("price"), rootOD.get("number"))));

            q.groupBy(b.function("YEAR", Integer.class, rootO.get("createdDate")));

            Query query = session.createQuery(q);
            List<Object[]> statsYear = query.getResultList();
            return statsYear;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Object[]> statsCategory() {
        try {
            Session session = this.factory.getObject().getCurrentSession();
            CriteriaBuilder b = session.getCriteriaBuilder();
            CriteriaQuery<Object[]> q = b.createQuery(Object[].class);

            Root rootC = q.from(Category.class);
            Root rootCS = q.from(CategorySub.class);
            Root rootP = q.from(Product.class);
            Root rootOD = q.from(OrderDetail.class);
            Root rootO = q.from(Orders.class);

            List<Predicate> predicates = new ArrayList<>();
            predicates.add(b.equal(rootCS.get("categoryId"), rootC.get("id")));
            predicates.add(b.equal(rootP.get("categorysubId"), rootCS.get("id")));
            predicates.add(b.equal(rootOD.get("productId"), rootP.get("id")));
            predicates.add(b.equal(rootOD.get("orderId"), rootO.get("id")));
            q.where(predicates.toArray(Predicate[]::new));

            q.multiselect(rootC.get("id"), rootC.get("name"), b.count(rootP.get("id")),
                    b.sum(b.prod(rootOD.get("price"), rootOD.get("number"))));
            q.groupBy(rootC.get("id"));
            q.orderBy(b.desc(b.sum(b.prod(rootOD.get("price"), rootOD.get("number")))));
            Query query = session.createQuery(q);
            List<Object[]> statsCategory = query.getResultList();
            return statsCategory;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Object[]> statsRevenue(Date fromDate, Date toDate) {
        try {
            Session session = this.factory.getObject().getCurrentSession();
            CriteriaBuilder b = session.getCriteriaBuilder();
            CriteriaQuery<Object[]> q = b.createQuery(Object[].class);

            Root rootC = q.from(Category.class);
            Root rootCS = q.from(CategorySub.class);
            Root rootP = q.from(Product.class);
            Root rootOD = q.from(OrderDetail.class);
            Root rootO = q.from(Orders.class);

            List<Predicate> predicates = new ArrayList<>();
            predicates.add(b.equal(rootCS.get("categoryId"), rootC.get("id")));
            predicates.add(b.equal(rootP.get("categorysubId"), rootCS.get("id")));
            predicates.add(b.equal(rootOD.get("productId"), rootP.get("id")));
            predicates.add(b.equal(rootOD.get("orderId"), rootO.get("id")));
            predicates.add(b.greaterThanOrEqualTo(rootO.get("createdDate").as(Date.class), fromDate));
            predicates.add(b.lessThanOrEqualTo(rootO.get("createdDate").as(Date.class), toDate));
            q.where(predicates.toArray(Predicate[]::new));

            q.multiselect(rootC.get("id"), rootC.get("name"), b.function("MONTH",
                    Integer.class, rootO.get("createdDate")),
                    b.sum(b.prod(rootOD.get("price"), rootOD.get("number"))));

            q.groupBy(b.function("MONTH", Integer.class, rootO.get("createdDate")));
            q.orderBy(b.asc(rootO.get("createdDate")));

            Query query = session.createQuery(q);
            List<Object[]> statsRevenue = query.getResultList();
            return statsRevenue;
        } catch (Exception e) {
            return null;
        }
    }

}
