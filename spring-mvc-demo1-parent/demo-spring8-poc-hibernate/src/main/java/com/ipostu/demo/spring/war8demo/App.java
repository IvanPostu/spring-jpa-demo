package com.ipostu.demo.spring.war8demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

public class App {

    public static void main(String[] args) {
        Configuration configuration = new Configuration()
                .addAnnotatedClass(Actor.class)
                .addAnnotatedClass(Movie.class);

        try (SessionFactory sessionFactory = configuration.buildSessionFactory();
             Session session = sessionFactory.getCurrentSession();) {

            session.beginTransaction();

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
        }
    }

}
