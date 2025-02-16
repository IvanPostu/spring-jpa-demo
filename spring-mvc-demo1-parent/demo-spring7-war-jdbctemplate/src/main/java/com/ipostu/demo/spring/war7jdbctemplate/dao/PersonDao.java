package com.ipostu.demo.spring.war7jdbctemplate.dao;

import com.ipostu.demo.spring.war7jdbctemplate.models.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDao {
    private static final Logger LOG = LoggerFactory.getLogger(PersonDao.class);

    private final JdbcTemplate jdbcTemplate;

    public PersonDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person selectByEmail(String email) {
        Object[] args = new Object[]{email};
        return jdbcTemplate.query("SELECT * FROM person WHERE email=?",
                        new BeanPropertyRowMapper<>(Person.class),
                        args)
                .stream().findAny().orElse(null);
    }

    public Person show(int id) {
        Object[] args = new Object[]{id};
        return jdbcTemplate.query("SELECT * FROM person WHERE id=?",
                        new BeanPropertyRowMapper<>(Person.class),
                        args)
                .stream().findAny().orElse(null);
    }

    public void save(Person person) {
        String sql = "INSERT INTO person (name, email, age) VALUES(?, ?, ?)";
        jdbcTemplate.update(sql, person.getName(), person.getEmail(), person.getAge());
    }

    public void update(int id, Person updatedPerson) {
        String sql = "UPDATE person SET name=?, email=?, age=? WHERE id=?";
        jdbcTemplate.update(sql,
                updatedPerson.getName(),
                updatedPerson.getEmail(),
                updatedPerson.getAge(),
                id);
    }

    public void delete(int id) {
        Object[] args = new Object[]{id};
        String sql = "DELETE FROM person WHERE id=?";
        jdbcTemplate.update(sql, args);
    }

    public long testMultipleUpdate() {
        List<Person> people = create1000People();

        long before = System.currentTimeMillis();

        for (Person person : people) {
            jdbcTemplate.update("INSERT INTO person (name, age, email) VALUES(?, ?, ?)",
                    person.getName(),
                    person.getAge(),
                    person.getEmail());
        }

        long after = System.currentTimeMillis();
        return after - before;
    }

    public long testBatchUpdate() {
        List<Person> people = create1000People();

        long before = System.currentTimeMillis();

        jdbcTemplate.batchUpdate("INSERT INTO person (name, age, email) VALUES (?, ?, ?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, people.get(i).getName());
                        ps.setInt(2, people.get(i).getAge());
                        ps.setString(3, people.get(i).getEmail());
                    }

                    @Override
                    public int getBatchSize() {
                        return people.size();
                    }
                });

        long after = System.currentTimeMillis();
        return after - before;
    }

    private List<Person> create1000People() {
        List<Person> people = new ArrayList<>(1000);

        for (int i = 0; i < 1000; i++)
            people.add(new Person(i, "Name" + i, 30, "test" + i + "@mail.ru"));

        return people;
    }
}
