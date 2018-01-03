package com.lapots.breed.dictionary.repository.controller;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import javax.annotation.PreDestroy;
import javax.inject.Singleton;

@Singleton
public class HibernateController {
    private SessionFactory sessionFactory;

    public HibernateController() {
        sessionFactory = new Configuration()
                .configure()
                .buildSessionFactory();
    }

    public Session openSession() {
        return sessionFactory.openSession();
    }

    @PreDestroy
    private void close() {
        sessionFactory.close();
        StandardServiceRegistryBuilder.destroy(sessionFactory.getSessionFactoryOptions().getServiceRegistry());
    }
}
