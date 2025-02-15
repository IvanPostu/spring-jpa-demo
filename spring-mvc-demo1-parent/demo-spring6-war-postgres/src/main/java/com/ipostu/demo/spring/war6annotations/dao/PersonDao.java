package com.ipostu.demo.spring.war6annotations.dao;

import com.ipostu.demo.spring.war6annotations.models.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class PersonDao {
    private static final Logger LOG = LoggerFactory.getLogger(PersonDao.class);

    @Autowired
    private MyCustomConnectionFactory connectionFactory;

    public List<Person> index() {
        List<Person> people = new ArrayList<>();

        try (Connection connection = connectionFactory.getConnection();
             Statement statement = connection.createStatement()) {

            String sql = "SELECT * FROM person";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Person person = new Person();

                person.setId(resultSet.getInt("id"));
                person.setName(resultSet.getString("name"));
                person.setEmail(resultSet.getString("email"));
                person.setAge(resultSet.getInt("age"));

                people.add(person);
            }

        } catch (SQLException e) {
            LOG.error("Operation failed", e);
        }
        return people;
    }

    public Person show(int id) {
        Person result = null;

        String sql = "SELECT * FROM person AS p WHERE p.id=?";
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Person person = new Person();

                person.setId(resultSet.getInt("id"));
                person.setName(resultSet.getString("name"));
                person.setEmail(resultSet.getString("email"));
                person.setAge(resultSet.getInt("age"));

                result = person;
            }

        } catch (SQLException e) {
            LOG.error("Operation failed", e);
        }

        return result;
    }

    public void save(Person person) {
        String sql = "INSERT INTO person (name, email, age) VALUES(?, ?, ?)";
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, person.getName());
            preparedStatement.setString(2, person.getEmail());
            preparedStatement.setInt(3, person.getAge());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Operation failed", e);
        }
    }

    public void update(int id, Person updatedPerson) {
        String sql = "UPDATE person SET name=?, email=?, age=? WHERE id=?";
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, updatedPerson.getName());
            preparedStatement.setString(2, updatedPerson.getEmail());
            preparedStatement.setInt(3, updatedPerson.getAge());
            preparedStatement.setInt(4, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Operation failed", e);
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM person WHERE id=?";
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Operation failed", e);
        }
    }
}
