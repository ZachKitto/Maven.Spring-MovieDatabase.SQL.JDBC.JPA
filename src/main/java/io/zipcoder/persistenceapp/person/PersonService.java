package io.zipcoder.persistenceapp.person;

import io.zipcoder.persistenceapp.person.Person;
import io.zipcoder.persistenceapp.person.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PersonService {

    @Autowired
    private PersonRepository repository;

    public Person create(Person newPerson) {
        return repository.save(newPerson);
    }

    public Person update(Long id, Person newPersonData) {
        Person currentPerson = findById(id);
        currentPerson.setFirstName(newPersonData.getFirstName());
        currentPerson.setLastName(newPersonData.getLastName());
        currentPerson.setMobile(newPersonData.getMobile());
        currentPerson.setBirthday(newPersonData.getBirthday());
        currentPerson.setHomeId(newPersonData.getHomeId());
        return repository.save(currentPerson);
    }

    public Person findById(Long id) {
        return repository.findOne(id);
    }

    public Person delete(Person personToDelete) {
        repository.delete(personToDelete);
        return personToDelete;
    }

    public Person delete(Long id) {
        return delete(findById(id));
    }

    public List<Person> deleteList(List<Person> people) {
        repository.delete(people);
        return people;
    }

    public List<Person> findAllByFirstName(String firstName) {
        return findAll()
                .stream()
                .filter(person -> person.getFirstName().equals(firstName))
                .collect(Collectors.toList());
    }

    public List<Person> findAllByLastName(String lastName) {
        return findAll()
                .stream()
                .filter(person -> person.getLastName().equals(lastName))
                .collect(Collectors.toList());
    }

    public List<Person> findAllByBirthdate(LocalDate birthdate) {
        return findAll()
                .stream()
                .filter(person -> person.getBirthday().equals(birthdate))
                .collect(Collectors.toList());
    }

    public List<Person> findAllByMobileNumber(String mobileNumber) {
        return findAll()
                .stream()
                .filter(person -> person.getMobile().equals(mobileNumber))
                .collect(Collectors.toList());
    }

    public List<Person> findAll() {
        return repository.findAll();
    }

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

    public Person addToHome(Long id, Long homeId) {
        Person personWithNewHome = findById(id);
        personWithNewHome.setHomeId(homeId);
        return personWithNewHome;
    }
}
