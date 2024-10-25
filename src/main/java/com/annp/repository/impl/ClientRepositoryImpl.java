package com.annp.repository.impl;

import com.annp.pojo.ClientInfo;
import com.annp.repository.ClientRepository;
import org.hibernate.HibernateException;
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
import java.util.List;

@Repository
@Transactional
public class ClientRepositoryImpl implements ClientRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public void saveClientInfo(ClientInfo clientInfo) {
        try {
            Session s = this.factory.getObject().getCurrentSession();
            s.save(clientInfo);
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public ClientInfo getClientInfoByUserId(int userId, String clientIp) {
        try {
            Session s = factory.getObject().getCurrentSession();
            CriteriaBuilder b = s.getCriteriaBuilder();
            CriteriaQuery<ClientInfo> q = b.createQuery(ClientInfo.class);
            Root root = q.from(ClientInfo.class);
            q.select(root);
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(b.equal(root.get("userId"), userId));
            predicates.add(b.equal(root.get("ip"), clientIp));
            predicates.add(b.equal(b.function("DAY", Integer.class, root.get("timestamp")),
                    b.function("DAY", Integer.class, b.currentDate())));
            q.where(predicates.toArray(Predicate[]::new));
            Query query = s.createQuery(q);
            ClientInfo c = (ClientInfo) query.getSingleResult();
            return c;
        } catch (HibernateException ex) {
            return null;
        }
    }

    @Override
    public void updateClientInfo(ClientInfo clientInfo) {
        try {
            Session s = this.factory.getObject().getCurrentSession();
            s.update(clientInfo);
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<ClientInfo> getAllClientInfoByUserId(int userId) {
        try {
            Session session = this.factory.getObject().getCurrentSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<ClientInfo> query = builder.createQuery(ClientInfo.class);
            Root root = query.from(ClientInfo.class);
            query = query.select(root);
            query.where(builder.equal(root.get("userId"), userId));
            query.orderBy(builder.desc(root.get("timestamp")));
            Query q = session.createQuery(query);
            List<ClientInfo> c = q.getResultList();
            return c != null && !c.isEmpty() ? c : new ArrayList<>();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
