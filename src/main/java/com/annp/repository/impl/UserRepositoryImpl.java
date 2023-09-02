package com.annp.repository.impl;

import com.annp.pojo.Users;
import com.annp.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
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
            query = query.select(root);

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
}
