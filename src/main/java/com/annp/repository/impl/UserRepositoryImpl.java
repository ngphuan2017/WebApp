package com.annp.repository.impl;

import com.annp.pojo.Status;
import com.annp.pojo.Users;
import com.annp.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;
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
import org.hibernate.HibernateException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Users getUserByUsername(String username) {
        Session s = factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Users> q = b.createQuery(Users.class);
        Root root = q.from(Users.class);
        q.select(root);

        q.where(b.equal(root.get("username"), username));

        Query query = s.createQuery(q);
        return (Users) query.getSingleResult();
    }

    @Override
    public boolean addOrUpdateUser(Users user) {
        Session session = this.factory.getObject().getCurrentSession();
        try {
            if (user.getId() != 0) {
                session.update(user);
            } else {
                session.save(user);
            }
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean getByUsername(String username) {
        Session s = factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Users> q = b.createQuery(Users.class);
        Root root = q.from(Users.class);
        q.select(root);
        q.where(b.equal(root.get("username"), username));
        Query query = s.createQuery(q);
        try {
            Users user = (Users) query.getSingleResult();
            return user != null ? true : false;
        } catch (NoResultException e) {
            return false;
        }
    }

    @Override
    public boolean updateUser(Users user) {
        Session session = this.factory.getObject().getCurrentSession();
        try {
            session.update(user);
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public Users getUserById(int id) {
        Session session = this.factory.getObject().getCurrentSession();
        return session.get(Users.class, id);
    }

    @Override
    public Users getUserByGoogleId(String googleId) {
        Session s = factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Users> q = b.createQuery(Users.class);
        Root root = q.from(Users.class);
        q.select(root);
        q.where(b.equal(root.get("googleID"), googleId));
        Query query = s.createQuery(q);
        try {
            Users user = (Users) query.getSingleResult();
            return user;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Users getUserByFacebookId(String facebookId) {
        Session s = factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Users> q = b.createQuery(Users.class);
        Root root = q.from(Users.class);
        q.select(root);
        q.where(b.equal(root.get("facebookID"), facebookId));
        Query query = s.createQuery(q);
        try {
            Users user = (Users) query.getSingleResult();
            return user;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Users> getUserByEmail(String email) {
        try {
            Session session = this.factory.getObject().getCurrentSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Users> query = builder.createQuery(Users.class);
            Root root = query.from(Users.class);
            query = query.multiselect(root.get("id"), root.get("fullname"), root.get("avatar"));

            if (!email.isEmpty()) {
                Predicate p = builder.equal(root.get("email"), email);
                query = query.where(p);
            }
            query = query.orderBy(builder.desc(root.get("id")));

            Query q = session.createQuery(query);

            List<Users> users = q.getResultList();
            return users != null && !users.isEmpty() ? users : new ArrayList<>();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Users getUserByTicket(String ticket) {
        Session s = factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Users> q = b.createQuery(Users.class);
        Root root = q.from(Users.class);
        q.select(root);
        q.where(b.equal(root.get("otp"), ticket));
        Query query = s.createQuery(q);
        try {
            Users user = (Users) query.getSingleResult();
            return user;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Users getUserAccountById(int id) {
        Session s = factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Users> q = b.createQuery(Users.class);
        Root root = q.from(Users.class);
        q.multiselect(root.get("id"), root.get("fullname"), root.get("avatar"),
                root.get("gender"), root.get("userstatus"),
                root.get("createdDate"), root.get("exp"));
        q.where(b.equal(root.get("id"), id));
        Query query = s.createQuery(q);
        try {
            Users user = (Users) query.getSingleResult();
            return user;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Users> getUsers(Map<String, String> params, int start, int limit) {
        try {
            Session s = factory.getObject().getCurrentSession();
            CriteriaBuilder b = s.getCriteriaBuilder();
            CriteriaQuery<Users> q = b.createQuery(Users.class);
            Root root = q.from(Users.class);
            q.select(root);

            if (params != null) {
                List<Predicate> predicates = new ArrayList<>();
                String kw = params.get("kw");
                if (kw != null && !kw.isEmpty()) {
                    Predicate p = b.like(root.get("fullname").as(String.class),
                            String.format("%%%s%%", kw));
                    predicates.add(p);
                }
                q.where(predicates.toArray(Predicate[]::new));
            }

            q.orderBy(b.desc(root.get("id")));
            Query query = s.createQuery(q);
            if (start > 0 && limit > 0) {
                query.setFirstResult(start - 1); // Vị trí bắt đầu
                query.setMaxResults(limit); // Số lượng kết quả trả về
            }
            return query.getResultList();
        } catch (Exception ex) {
            return null;
        }

    }

    @Override
    public boolean deleteCustomer(int id) {
        Users user = this.getUserById(id);
        user.setUserstatus(new Status(4));
        Session s = this.factory.getObject().getCurrentSession();
        try {
            s.update(user);
            return true;
        } catch (HibernateException ex) {
            return false;
        }
    }
}
