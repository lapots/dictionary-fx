package com.lapots.breed.dictionary.repository;

import com.lapots.breed.dictionary.repository.controller.HibernateController;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

public class QueryTemplate {
    private HibernateController hibernateController;

    @Inject
    public QueryTemplate(HibernateController controller) {
        this.hibernateController = controller;
    }

    public <T> void insert(List<T> objects) {
        try (Session session = hibernateController.openSession()) {
            Transaction tx = session.beginTransaction();

            // generalize
            objects.forEach(session::save);

            tx.commit();
        }
    }

    public <T> void update(T object) {}

    public <T> void delete(T id) {}

    @SuppressWarnings("unchecked")
    public <T> List<T> select(String query) {
        List<T> result = Collections.emptyList();
        try (Session session = hibernateController.openSession()) {
            result = session.createQuery(query).list();
        }
        return result;
    }

    public <T, K> List<T> select(K clazz) {
        return Collections.emptyList();
    };
}
