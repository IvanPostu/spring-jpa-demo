package com.ipostu.demo.spring.war8demo;

import jakarta.persistence.Query;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

public class App {

    public static void main(String[] args) {
        Configuration configuration = new Configuration()
                .addAnnotatedClass(Actor.class)
                .addAnnotatedClass(Movie.class)
                .addAnnotatedClass(Passport.class)
                .addAnnotatedClass(Passport2.class)
                .addAnnotatedClass(Item.class);

        try (SessionFactory sessionFactory = configuration.buildSessionFactory();
        ) {
            Session session = sessionFactory.getCurrentSession();

            session.beginTransaction();

//            createMovieWithTwoActors(session);
//            selectActors(session);
//            insertActorWithItems(session);
//            insertActorWithPassport(session);
//            insertActorWithPassport2(session);
//            useLazyEntitiesOutsideOfPersistenceContext(session, sessionFactory);
//            reatachDetachedEntityToAnotherPersistenceContext(session, sessionFactory);
        }
    }

    private static void reatachDetachedEntityToAnotherPersistenceContext(Session session, SessionFactory sessionFactory) {
        Movie movie = new Movie("Movie example", 2002);
        Actor actor1 = new Actor("Actor 1 Name", 44);
        Actor actor2 = new Actor("Actor 2 Name", 22);

        movie.setActors(new ArrayList<>(List.of(actor1, actor2)));
        actor1.setMovies(new ArrayList<>(List.of(movie)));
        actor2.setMovies(new ArrayList<>(List.of(movie)));

        session.save(movie);
        session.save(actor1);
        session.save(actor2);

        session.getTransaction().commit();


        session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        movie = session.merge(movie);

        Hibernate.initialize(movie.getActors());
        // System.out.println(movie2.getActors()); similar to Hibernate.initialize(movie2.getActors());
        // movie2.getActors() won't work because java will optimize/remove this call because it isn't used

        session.getTransaction().commit();

        System.out.println(movie.getActors());
    }

    private static void useLazyEntitiesOutsideOfPersistenceContext(Session session, SessionFactory sessionFactory) {
        Movie movie = new Movie("Movie example", 2002);
        Actor actor1 = new Actor("Actor 1 Name", 44);
        Actor actor2 = new Actor("Actor 2 Name", 22);

        movie.setActors(new ArrayList<>(List.of(actor1, actor2)));
        actor1.setMovies(new ArrayList<>(List.of(movie)));
        actor2.setMovies(new ArrayList<>(List.of(movie)));

        session.save(movie);
        session.save(actor1);
        session.save(actor2);

        session.getTransaction().commit();


        session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Movie movie2 = session.get(Movie.class, movie.getId());

        System.out.println(
                session.createQuery("SELECT i FROM Item i WHERE i.owner.id=:personId", Item.class)
                        .setParameter("personId", actor1.getId())
                        .getResultList()
        );

        Hibernate.initialize(movie2.getActors());
        // System.out.println(movie2.getActors()); similar to Hibernate.initialize(movie2.getActors());
        // movie2.getActors() won't work because java will optimize/remove this call because it isn't used

        session.getTransaction().commit();

        System.out.println(movie2.getActors());
    }

    private static void insertActorWithPassport2(Session session) {
        Actor actor = new Actor("name example", 22);
        Passport2 passport2 = new Passport2(actor, 12345);
        actor.setPassport2(passport2);

        session.save(actor);
    }

    private static void insertActorWithPassport(Session session) {
        Actor actor = new Actor("name example", 22);
        Passport passport = new Passport(actor, 12345);
        actor.setPassport(passport);

        session.save(actor);
    }

    private static void insertActorWithItems(Session session) {
        Actor actor = new Actor("Name example 1", 22);
        Item item1 = new Item("example 1");
        item1.setOwner(actor);
        Item item2 = new Item("example 2");
        item2.setOwner(actor);

        actor.setItems(new ArrayList<>(List.of(item1, item2)));
        session.save(actor);
        session.save(item1);
        session.save(item2);

//        Actor selectedActor = session.get(Actor.class, 4);
    }

    private static void selectActors(Session session) {
        Query query = session.createQuery("FROM Actor WHERE 1=:example");
        query.setParameter("example", Integer.valueOf(1));

        List<Actor> users = query.getResultList();
    }


    private static void createMovieWithTwoActors(Session session) {
        Movie movie = new Movie("Movie example", 2002);
        Actor actor1 = new Actor("Actor 1 Name", 44);
        Actor actor2 = new Actor("Actor 2 Name", 22);

        movie.setActors(new ArrayList<>(List.of(actor1, actor2)));
        actor1.setMovies(new ArrayList<>(List.of(movie)));
        actor2.setMovies(new ArrayList<>(List.of(movie)));

        session.save(movie);
        session.save(actor1);
        session.save(actor2);
    }

}
