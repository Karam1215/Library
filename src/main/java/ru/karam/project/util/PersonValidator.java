package ru.karam.project.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.karam.project.dao.PersonDao;
import ru.karam.project.model.Person;

@Component
public class PersonValidator implements Validator {
    private final PersonDao personDao;

    public PersonValidator(PersonDao personDao) {
        this.personDao = personDao;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if(personDao.getPersonByName(person.getName()).isPresent())
            errors.rejectValue("name", "", "Человек с таким ФИО уже существует");

    }
}
