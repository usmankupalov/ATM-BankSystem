package com.example.atmbanksystem.service.impl;

import com.example.atmbanksystem.hibernateConfig.HibernateUtil;
import org.hibernate.Session;

import javax.persistence.NoResultException;
import java.util.function.Function;

public class Transaction<T> {

    private final Function<Session, T> action;

    private Transaction(Function<Session, T> action) {
        this.action = action;
    }

    public static <U> Transaction<U> of (Function<Session, U> action) {
        return new Transaction<U>(action);
    }

    public T run() {
        org.hibernate.Transaction transaction = null;
        T result = null;
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();
            result = action.apply(session);
            transaction.commit();
            return result;
        }  catch (NoResultException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return result;
    }
}
