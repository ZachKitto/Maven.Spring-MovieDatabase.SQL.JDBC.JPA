package io.zipcoder.persistenceapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.http.HTTPException;
import java.util.List;
import java.util.Map;

@RestController
public class PersonController {

    @Autowired
    PersonService service;

    @PostMapping(value = "/people")
    public ResponseEntity<Person> create(@RequestBody Person person) {
        return new ResponseEntity<>(service.create(person), HttpStatus.CREATED);
    }

    @PutMapping(value = "/people/{id}")
    public ResponseEntity<Person> update(@RequestBody Person newPersonData, @PathVariable Long id) {
        try {
            return new ResponseEntity<>(service.update(id, newPersonData), HttpStatus.OK);
        }
        catch (Exception e) {
            throw new HTTPException(404);
        }
    }

    @GetMapping(value = "/people/{id}")
    public ResponseEntity<Person> findById(@PathVariable Long id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @DeleteMapping(value = "/people/{id}")
    public ResponseEntity<Person> deleteById(@PathVariable Long id) {
        return new ResponseEntity<>(service.delete(id), HttpStatus.OK);
    }

    @GetMapping(value = "/people")
    public ResponseEntity<List<Person>> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/people/reverselookup/{mobileNumber}")
    public ResponseEntity<List<Person>> findAllByMobileNumber(@PathVariable String mobileNumber) {
        return new ResponseEntity<>(service.findAllByMobileNumber(mobileNumber), HttpStatus.OK);
    }

    @GetMapping(value = "/people/surname/{lastName}")
    public ResponseEntity<List<Person>> findAllBySurname(@PathVariable String lastName) {
        return new ResponseEntity<>(service.findAllByLastName(lastName), HttpStatus.OK);
    }

    @GetMapping(value = "/people/surname")
    public ResponseEntity<Map<String, List<Person>>> mapOfSurnames() {
        return new ResponseEntity<>(service.generateMapOfSurnames(), HttpStatus.OK);
    }

    @GetMapping(value = "/people/firstname/stats")
    public ResponseEntity<Map<String, Integer>> mapOfFirstNameOccurrences() {
        return new ResponseEntity<>(service.generateMapOfNumberOfFirstNameOccurrences(), HttpStatus.OK);
    }
}
