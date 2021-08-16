package io.zipcoder.persistenceapp.person;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface PersonService {

    Person create(Person newPerson);

    Person update(Long id, Person newPersonData);

    Person findById(Long id);

    Person delete(Person personToDelete);

    Person delete(Long id);

    List<Person> deleteList(List<Person> people);

    List<Person> findAllByFirstName(String firstName);

    List<Person> findAllByLastName(String lastName);

    List<Person> findAllByBirthdate(LocalDate birthdate);

    List<Person> findAllByMobileNumber(String mobileNumber);

    List<Person> findAll();

    Map<String, List<Person>> generateMapOfSurnames();

    Map<String, Integer> generateMapOfNumberOfFirstNameOccurrences();

    Person addToHome(Long id, Long homeId);
}
