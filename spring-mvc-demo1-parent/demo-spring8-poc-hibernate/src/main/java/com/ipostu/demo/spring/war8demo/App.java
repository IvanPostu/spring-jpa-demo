package com.ipostu.demo.spring.war8demo;

import jakarta.persistence.Query;
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
                .addAnnotatedClass(Item.class);

        try (SessionFactory sessionFactory = configuration.buildSessionFactory();
             Session session = sessionFactory.getCurrentSession();) {

            session.beginTransaction();

//            createMovieWithTwoActors(session);
//            selectActors(session);
//            insertActorWithItems(session);

            Actor actor = new Actor("name example", 22);
            Passport passport = new Passport(actor, 12345);
            actor.setPassport(passport);

            session.save(actor);

            session.getTransaction().commit();
        }
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
