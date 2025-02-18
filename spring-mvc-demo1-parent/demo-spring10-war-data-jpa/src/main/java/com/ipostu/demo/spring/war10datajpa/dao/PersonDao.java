package com.ipostu.demo.spring.war10datajpa.dao;

import com.ipostu.demo.spring.war10datajpa.models.Person;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDao {
    private static final Logger LOG = LoggerFactory.getLogger(PersonDao.class);

    private final SessionFactory sessionFactory;

    public PersonDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Person> index() {
        Session session = sessionFactory.getCurrentSession();
        List<Person> people = session.createQuery("select p from Person p", Person.class)
                .getResultList();
        return people;
    }

    @Transactional(readOnly = true)
    public Person selectByEmail(String email) {
        Session session = sessionFactory.getCurrentSession();
        Person person = session
                .createQuery("select p from Person p WHERE p.email=:email", Person.class)
                .setParameter("email", email)
                .getSingleResultOrNull();
        return person;
    }

    @Transactional(readOnly = true)
    public Person show(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session
                .createQuery("select p from Person p WHERE p.id=:id", Person.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Transactional
    public void save(Person person) {
        Session session = sessionFactory.getCurrentSession();
        session.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson) {
        Session session = sessionFactory.getCurrentSession();
        updatedPerson.setId(id);
        updatedPerson = session.merge(updatedPerson);
        session.save(updatedPerson);
    }

    @Transactional
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        session
                .createQuery("DELETE FROM Person p WHERE p.id = :personId")
                .setParameter("personId", id)
                .executeUpdate();
    }

    public long testMultipleUpdate() {
        List<Person> people = create1000People();

        long before = System.currentTimeMillis();

        for (Person person : people) {
            Session session = getOrOpenSession();
            session.beginTransaction();
            session.save(person);
            session.getTransaction().commit();
        }

        long after = System.currentTimeMillis();
        return after - before;
    }

    public long testBatchUpdate() {
        List<Person> people = create1000People();
        Session session = getOrOpenSession();
        long before = System.currentTimeMillis();

        session.beginTransaction();
        for (Person person : people) {
            session.save(person);
        }
        session.getTransaction().commit();

        long after = System.currentTimeMillis();
        return after - before;
    }

    private List<Person> create1000People() {
        List<Person> people = new ArrayList<>(1000);

        for (int i = 0; i < 1000; i++) {
            people.add(new Person(i, "Name" + i, 30, "test" + i + "@mail.ru", "Aaa, Aaa, 123456"));
        }

        return people;
    }

    private Session getOrOpenSession() {
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            session = sessionFactory.openSession();
        }
        return session;
    }
}
