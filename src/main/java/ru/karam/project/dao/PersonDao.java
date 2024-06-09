package ru.karam.project.dao;


import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.karam.project.model.Book;
import ru.karam.project.model.Person;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDao {
    private final JdbcTemplate jdbcTemplate;

    public PersonDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<Person> getAll() {
        return  jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person getById(Integer id) {
        return jdbcTemplate.query("SELECT * FROM person WHERE person_id = ?", new Object[]{id},new BeanPropertyRowMapper<>(Person.class))
                .stream().findFirst().orElse(null);
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO person(name,age) Values (?,?)", person.getName(), person.getAge());
    }

    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("UPDATE person SET NAME=?, AGE=? WHERE person_id = ?", updatedPerson.getName(), updatedPerson.getAge(), id);
    }

    public void delete(Integer id) {jdbcTemplate.update("DELETE FROM person WHERE person_id = ?", id);}

    public List<Book> getCurrentBook(Integer person_id) {
        return jdbcTemplate.query("SELECT * FROM book where person_id=?",
                new Object[]{person_id},
                new BeanPropertyRowMapper<>(Book.class));
    }

    public Optional<Person> getPersonByName(String name) {
        return jdbcTemplate.query("SELECT * FROM person WHERE name=?",
                new Object[]{name},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

}
