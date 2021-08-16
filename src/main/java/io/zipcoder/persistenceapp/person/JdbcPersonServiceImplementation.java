package io.zipcoder.persistenceapp.person;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlRowSetResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Primary
@Service
public class JdbcPersonServiceImplementation implements PersonService {

    Connection connection = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");
    Statement statement = connection.createStatement();

    public JdbcPersonServiceImplementation() throws SQLException {

    }

    @Override
    public Person create(Person newPerson) {
        try {
            String query = String.format("INSERT INTO person (first_name, last_name, mobile, birthday, home_id) " +
                    "VALUES ('%s', '%s', '%s', '%s', %d)", newPerson.getFirstName(), newPerson.getLastName(), newPerson.getMobile(), newPerson.getBirthday(), newPerson.getHomeId());
            statement.executeUpdate(query);
            return find(newPerson);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return new Person();
        }
    }

    @Override
    public Person update(Long id, Person newPersonData) {
        try {
            String query = String.format("UPDATE person SET first_name = '%s', last_name = '%s', mobile = '%s', birthday = '%s', home_id = %d " +
                    "WHERE id = %d", newPersonData.getFirstName(), newPersonData.getLastName(), newPersonData.getMobile(), newPersonData.getBirthday(), newPersonData.getHomeId(), id);
            statement.executeUpdate(query);
            return find(newPersonData);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return new Person();
        }
    }

    @Override
    public Person findById(Long id) {
        try {
            String query = String.format("SELECT * FROM person WHERE id = %d", id);
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();
            return new Person(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getInt(6));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return new Person();
        }
    }

    public Person find(Person person) {
        try {
            String query = String.format("SELECT * FROM person " +
                    "WHERE first_name LIKE '%s'" +
                    "AND last_name LIKE '%s'" +
                    "AND mobile LIKE '%s'" +
                    "AND birthday LIKE '%s'" +
                    "AND home_id = %d", person.getFirstName(), person.getLastName(), person.getMobile(), person.getBirthday(), person.getHomeId());
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();
            return new Person(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getInt(6));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return new Person();
        }
    }

    @Override
    public Person delete(Person personToDelete) {
        try {
            Person personDeleted = find(personToDelete);
            String query = String.format("DELETE FROM person " +
                    "WHERE first_name LIKE '%s'" +
                    "AND last_name LIKE '%s'" +
                    "AND mobile LIKE '%s'" +
                    "AND birthday LIKE '%s'" +
                    "AND home_id = %d)", personToDelete.getFirstName(), personToDelete.getLastName(), personToDelete.getMobile(), personToDelete.getBirthday(), personToDelete.getHomeId());
            statement.executeUpdate(query);
            return personDeleted;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return new Person();
        }
    }

    @Override
    public Person delete(Long id) {
        try {
            Person personDeleted = findById(id);
            String query = String.format("DELETE FROM person " +
                    "WHERE id = %d", id);
            statement.executeUpdate(query);
            return personDeleted;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return new Person();
        }
    }

    @Override
    public List<Person> deleteList(List<Person> people) {
        List<Person> listOfPeople = new ArrayList<>();
        for (Person personToDelete : people) {
            try {
                listOfPeople.add(find(personToDelete));
                String query = String.format("DELETE FROM person " +
                        "WHERE first_name LIKE '%s'" +
                        "AND last_name LIKE '%s'" +
                        "AND mobile LIKE '%s'" +
                        "AND birthday LIKE '%s'" +
                        "AND home_id = %d)", personToDelete.getFirstName(), personToDelete.getLastName(), personToDelete.getMobile(), personToDelete.getBirthday(), personToDelete.getHomeId());
                statement.executeUpdate(query);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return listOfPeople;
    }

    @Override
    public List<Person> findAllByFirstName(String firstName) {
        List<Person> listOfPeople = new ArrayList<>();
        try {
            String query = String.format("SELECT * FROM person " +
                    "WHERE first_name LIKE '%s'", firstName);
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Person person = new Person(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getInt(6));
                listOfPeople.add(person);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return listOfPeople;
    }

    @Override
    public List<Person> findAllByLastName(String lastName) {
        List<Person> listOfPeople = new ArrayList<>();
        try {
            String query = String.format("SELECT * FROM person " +
                    "WHERE last_name LIKE '%s'", lastName);
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Person person = new Person(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getInt(6));
                listOfPeople.add(person);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return listOfPeople;
    }

    @Override
    public List<Person> findAllByBirthdate(LocalDate birthdate) {
        List<Person> listOfPeople = new ArrayList<>();
        try {
            String query = String.format("SELECT * FROM person " +
                    "WHERE birthday LIKE '%s'", birthdate);
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Person person = new Person(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getInt(6));
                listOfPeople.add(person);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return listOfPeople;
    }

    @Override
    public List<Person> findAllByMobileNumber(String mobileNumber) {
        List<Person> listOfPeople = new ArrayList<>();
        try {
            String query = String.format("SELECT * FROM person " +
                    "WHERE mobile LIKE '%s'", mobileNumber);
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Person person = new Person(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getInt(6));
                listOfPeople.add(person);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return listOfPeople;
    }

    @Override
    public List<Person> findAll() {
        List<Person> listOfPeople = new ArrayList<>();
        try {
            String query = String.format("SELECT * FROM person");
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Person  person = new Person(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getInt(6));
                listOfPeople.add(person);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return listOfPeople;
    }

    @Override
    public Map<String, List<Person>> generateMapOfSurnames() {
        Map<String, List<Person>> map = new HashMap<>();
        findAll()
                .stream()
                .forEach(person -> {
                    String lastName = person.getLastName();
                    List<Person> peopleWithThisLastName = findAllByLastName(lastName);
                    map.put(lastName, peopleWithThisLastName);
                });
        return map;
    }

    @Override
    public Map<String, Integer> generateMapOfNumberOfFirstNameOccurrences() {
        Map<String, Integer> map = new HashMap<>();
        findAll()
                .stream()
                .forEach(person -> {
                    String firstName = person.getFirstName();
                    if (!map.containsKey(firstName)) {
                        map.put(firstName, 1);
                    }
                    else {
                        Integer numberOfOccurrences = map.get(firstName);
                        numberOfOccurrences++;
                        map.replace(firstName, numberOfOccurrences);
                    }
                });
        return map;
    }

    @Override
    public Person addToHome(Long id, Long homeId) {
        Person personWithNewHome = findById(id);
        personWithNewHome.setHomeId(homeId);
        return personWithNewHome;
    }
}
