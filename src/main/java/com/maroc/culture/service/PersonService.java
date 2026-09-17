package com.maroc.culture.service;

import com.maroc.culture.entity.Person;
import com.maroc.culture.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public Optional<Person> getPersonsById(Long id) {
        return personRepository.findById(id);
    }

    public Boolean deletePersonById(Long id) {
        if (personRepository.existsById(id)) {
            personRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Boolean deleteAllPerson() {
        personRepository.deleteAll();
        return true;
    }

    public Person createPerson(Person person) {
        if (personRepository.existsByNameIgnoreCase(person.getName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cette personne existe déjà !");
        }
        person.setId(null); // باش نضمنو ديما INSERT جديد
        return personRepository.save(person);
    }

    public Optional<Person> updatePerson(Long id, Person updated) {
        return personRepository.findById(id).map(p -> {
            p.setName(updated.getName());
            p.setAge(updated.getAge());
            return personRepository.save(p);
        });
        /*
            Optional<Person> optional = personRepository.findById(id);
            if (optional.isPresent()) {
                Person p = optional.get();
                p.setName(updated.getName());
                ...
                return Optional.of(personRepository.save(p));
            }
            return Optional.empty();
        */
    }
}