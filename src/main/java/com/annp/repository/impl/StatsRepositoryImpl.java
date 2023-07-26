package com.annp.repository.impl;

import com.annp.pojo.Category;
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
    public List<Object[]> statsCategory() {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = b.createQuery(Object[].class);

        Root rootP = q.from(Product.class);
        Root rootC = q.from(Category.class);
        q.where(b.equal(rootP.get("categoryId"), rootC.get("id")));

        q.multiselect(rootC.get("id"), rootC.get("name"), b.count(rootP.get("id")));
        q.groupBy(rootC.get("id"));

        Query query = session.createQuery(q);
        return query.getResultList();

    }

    @Override
    public List<Object[]> statsRevenue(Date fromDate, Date toDate) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = b.createQuery(Object[].class);

        Root rootP = q.from(Product.class);
        Root rootD = q.from(OrderDetail.class);
        Root rootO = q.from(Orders.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(b.equal(rootD.get("productId"), rootP.get("id")));
        predicates.add(b.equal(rootD.get("orderId"), rootO.get("id")));

        q.multiselect(rootP.get("id"), rootP.get("name"),
                b.sum(b.prod(rootD.get("num"), rootD.get("unitPrice"))));

        if (fromDate != null) {
            predicates.add(b.greaterThanOrEqualTo(rootO.get("createdDate").as(Date.class), fromDate));
        }

        if (toDate != null) {
            predicates.add(b.lessThanOrEqualTo(rootO.get("createdDate").as(Date.class), toDate));
        }

        q.where(predicates.toArray(Predicate[]::new));

        q.groupBy(rootP.get("id"));
        q.orderBy(b.desc(rootP.get("id")));

        Query query = session.createQuery(q);
        return query.getResultList();

    }
}
