package vn.iotstar.dao.impl;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import vn.iotstar.config.JpaConfig;
import vn.iotstar.dao.ICategoryDao;
import vn.iotstar.entity.Category;

public class CategoryDaoImpl implements ICategoryDao {

    @Override
    public List<Category> findAll() {
        EntityManager em = JpaConfig.getEntityManager();
        try {
            TypedQuery<Category> query = em.createQuery(
                    "SELECT c FROM Category c ORDER BY c.categoryId DESC", Category.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Category findById(Integer id) {
        EntityManager em = JpaConfig.getEntityManager();
        try {
            return em.find(Category.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public Category save(Category category) {
        EntityManager em = JpaConfig.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(category);
            tx.commit();
            return category;
        } catch (RuntimeException e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public Category update(Category category) {
        EntityManager em = JpaConfig.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Category merged = em.merge(category);
            tx.commit();
            return merged;
        } catch (RuntimeException e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean deleteById(Integer id) {
        EntityManager em = JpaConfig.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Category category = em.find(Category.class, id);
            if (category != null) {
                em.remove(category);
                tx.commit();
                return true;
            }
            tx.rollback();
            return false;
        } catch (RuntimeException e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }
}
