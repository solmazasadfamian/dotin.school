package com.dotin.timeOffRequest.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class GenericDaoImpl<entityType, PK extends Serializable> implements GenericDao<entityType, PK> {

    private final Class<entityType> classType;
    private final String className;

    private Session currentSession;
    private Transaction currentTransaction;

    @SuppressWarnings("unchecked")
    public GenericDaoImpl() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        classType = (Class<entityType>) pt.getActualTypeArguments()[0];
        String templateName = classType.getName();
        this.className = templateName.substring(templateName.lastIndexOf('.') + 1);
    }

    private static SessionFactory getSessionFactory() {

        Configuration configuration = new Configuration().configure();
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml");
        SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());
        return sessionFactory;
    }

    public Session openCurrentSession() {
        currentSession = getSessionFactory().openSession();
        return currentSession;
    }

    public Session openCurrentSessionWithTransaction() {
        currentSession = getSessionFactory().openSession();
        currentTransaction = currentSession.beginTransaction();
        return currentSession;
    }

    public void closeCurrentSession() {
        currentSession.close();
    }

    public void closeCurrentSessionWithTransaction() {
        currentTransaction.commit();
        currentSession.close();
    }

    public Session getCurrentSession() {
        return currentSession;
    }

    public void setCurrentSession(Session currentSession) {
        this.currentSession = currentSession;
    }

    public Transaction getCurrentTransaction() {
        return currentTransaction;
    }

    public void setCurrentTransaction(Transaction currentTransaction) {
        this.currentTransaction = currentTransaction;
    }

    @Override
    public entityType insert(entityType t) {
        currentSession.save(t);
        return t;
    }

    @Override
    public void delete(PK id) {
        currentSession.delete(getEntity(id));
    }

    @Override
    public entityType update(entityType newObject) {
        currentSession.merge(newObject);
        return newObject;
    }


    @Override
    public entityType getEntity(PK id) {
        return currentSession.get(classType, id);
    }

    @Override
    public List<entityType> selectAll() {
        List<entityType> result = null;
        Criteria criteria = currentSession.createCriteria(this.classType);
        criteria.add(Restrictions.eq("active" , true));
        result = criteria.list();
        return result;
    }

}
