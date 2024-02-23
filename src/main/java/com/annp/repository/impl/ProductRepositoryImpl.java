package com.annp.repository.impl;

import com.annp.pojo.Cart;
import com.annp.pojo.Orders;
import com.annp.pojo.OrderDetail;
import com.annp.pojo.Product;
import com.annp.pojo.ProductImages;
import com.annp.pojo.Status;
import com.annp.repository.ProductRepository;
import com.annp.repository.UserRepository;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
public class ProductRepositoryImpl implements ProductRepository {

    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Product> getProducts(Map<String, String> params, int start, int limit) {
        try {
            Session s = factory.getObject().getCurrentSession();
            CriteriaBuilder b = s.getCriteriaBuilder();
            CriteriaQuery<Product> q = b.createQuery(Product.class);
            Root root = q.from(Product.class);
            q.select(root);

            if (params != null) {
                List<Predicate> predicates = new ArrayList<>();
                String kw = params.get("kw");
                if (kw != null && !kw.isEmpty()) {
                    Predicate p = b.like(root.get("name").as(String.class),
                            String.format("%%%s%%", kw));
                    predicates.add(p);
                }

                String cateId = params.get("categorysubId");
                if (cateId != null && cateId.matches("^\\d+$")) {
                    Predicate p = b.equal(root.get("categorysubId"), Integer.parseInt(cateId));
                    predicates.add(p);
                }
                q.where(predicates.toArray(Predicate[]::new));
            }

            q.orderBy(b.desc(root.get("unitsSold")));
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
    public Product getProductById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(Product.class, id);
    }

    @Override
    public boolean addOrUpdateProduct(Product p) {
        Session s = this.factory.getObject().getCurrentSession();
        try {
            if (p.getId() > 0) {
                s.update(p);
            } else {
                s.save(p);
            }

            return true;
        } catch (HibernateException ex) {
            return false;
        }
    }

    @Override
    public boolean deleteProduct(int id) {
        Product p = this.getProductById(id);
        p.setProductstatus(new Status(7));
        Session s = this.factory.getObject().getCurrentSession();
        try {
            s.update(p);
            return true;
        } catch (HibernateException ex) {
            return false;
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean addReceipt(Map<String, Cart> cart, Integer amount) {
        Session s = this.factory.getObject().getCurrentSession();

        try {
            Orders r = new Orders();
            r.setId(0);
            r.setUserid(userRepository.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
            r.setAmount(amount);
            r.setType(new Status(17));
            r.setCreatedDate(new Date());
            s.save(r);

            for (Cart c : cart.values()) {
                OrderDetail d = new OrderDetail();
                d.setId(0);
                d.setPrice(c.getPrice());
                d.setNumber(c.getQuantity());
                d.setOrderstatus(new Status(9));
                d.setOrderId(r);
                d.setProductId(this.getProductById(c.getId()));
                d.setCreatedDate(new Date());
                s.save(d);
            }
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean addReceiptPaid(Map<String, Cart> cart, Integer amount) {
        Session s = this.factory.getObject().getCurrentSession();

        try {
            Orders r = new Orders();
            r.setId(0);
            r.setUserid(userRepository.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
            r.setAmount(amount);
            r.setType(new Status(18));
            r.setCreatedDate(new Date());
            s.save(r);

            for (Cart c : cart.values()) {
                OrderDetail d = new OrderDetail();
                d.setId(0);
                d.setPrice(c.getPrice());
                d.setNumber(c.getQuantity());
                d.setOrderstatus(new Status(9));
                d.setOrderId(r);
                d.setProductId(this.getProductById(c.getId()));
                d.setCreatedDate(new Date());
                s.save(d);
            }
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateProduct(Product p) {
        Session s = this.factory.getObject().getCurrentSession();
        try {
            s.update(p);
            return true;
        } catch (HibernateException ex) {
            return false;
        }
    }

    @Override
    public ProductImages getImagesByProductId(Product product) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<ProductImages> query = builder.createQuery(ProductImages.class);
        Root root = query.from(ProductImages.class);
        query = query.select(root);
        query = query.where(builder.equal(root.get("productId"), product));

        Query q = session.createQuery(query);

        try {
            ProductImages p = (ProductImages) q.getSingleResult();
            return p;
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public boolean addOrUpdateProductImages(ProductImages img) {
        Session s = this.factory.getObject().getCurrentSession();
        try {
            if (img.getId() > 0) {
                s.update(img);
            } else {
                s.save(img);
            }

            return true;
        } catch (HibernateException ex) {
            return false;
        }
    }
    
}
