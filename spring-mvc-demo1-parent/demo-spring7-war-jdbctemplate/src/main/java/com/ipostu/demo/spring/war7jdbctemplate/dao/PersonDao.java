package com.ipostu.demo.spring.war7jdbctemplate.dao;

import com.ipostu.demo.spring.war7jdbctemplate.models.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

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
}
