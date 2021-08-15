package io.zipcoder.persistenceapp.home;

import io.zipcoder.persistenceapp.person.Person;
import io.zipcoder.persistenceapp.person.PersonRepository;
import io.zipcoder.persistenceapp.person.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HomeService {

    @Autowired
    HomeRepository repository;

    @Autowired
    PersonService personService;

    public Home create(Home home) {
        return repository.save(home);
    }

    public Home findById(Long id) {
        return repository.findOne(id);
    }

    public List<Home> findAll() {
        return repository.findAll();
    }

    public Home update(Long id, Home newHouseData) {
        Home houseToUpdate = findById(id);
        houseToUpdate.setAddress(newHouseData.getAddress());
        houseToUpdate.setHomeNumber(newHouseData.getHomeNumber());
        return houseToUpdate;
    }

    public Home delete(Home houseToDelete) {
        repository.delete(houseToDelete);
        return houseToDelete;
    }

    public Home delete(Long id) {
        Home houseToDelete = findById(id);
        return delete(houseToDelete);
    }

    public List<Home> deleteList(List<Home> houses) {
        repository.delete(houses);
        return houses;
    }

    public Home findByHomeNumber(String homeNumber) {
        return findAll()
                .stream()
                .filter(home -> home.getHomeNumber().equals(homeNumber))
                .findAny()
                .get();
    }

    public Home findByAddress(String address) {
        return findAll()
                .stream()
                .filter(home -> home.getAddress().equals(address))
                .findAny()
                .get();
    }

    public Home findByPersonId(Long id) {
        Person personInHome = personService.findById(id);
        Long idOfPersonsHome = personInHome.getHomeId();
        return findById(idOfPersonsHome);
    }

    public List<Person> findPeopleInHome(Home home) {
        List<Person> allPeople = personService.findAll();
        Long idOfHome = home.getId();
        return allPeople
                .stream()
                .filter(person -> person.getHomeId() == idOfHome)
                .collect(Collectors.toList());
    }

    public List<Person> findPeopleInHomeById(Long id) {
        List<Person> allPeople = personService.findAll();
        return allPeople
                .stream()
                .filter(person -> person.getHomeId() == id)
                .collect(Collectors.toList());
    }
}
