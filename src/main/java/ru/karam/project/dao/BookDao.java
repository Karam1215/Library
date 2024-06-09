package ru.karam.project.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.karam.project.model.Book;
import ru.karam.project.model.Person;

import java.util.List;
import java.util.Optional;

@Component
public class BookDao {
    private final JdbcTemplate jdbcTemplate;


    public BookDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<Book> getBooks() {
        return jdbcTemplate.query("select * from book", new BeanPropertyRowMapper<>(Book.class));
    }
    public Book getBook(int id) {
        return jdbcTemplate.query("select * from book where book_id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class)).stream().findFirst().orElse(null);
    }

    public void addBook(Book book) {
         jdbcTemplate.update("INSERT INTO book (title, author,year) VALUES (?, ?, ?)", book.getTitle(), book.getAuthor(), book.getYear());
    }
    public void updateBook(int id, Book updatedBook) {
        jdbcTemplate.update("UPDATE book SET title=?, author=?, year=? WHERE book_id=?",
                updatedBook.getTitle(), updatedBook.getAuthor(), updatedBook.getYear(), id);
    }
    public void deleteBook(int id) {
        jdbcTemplate.update("DELETE FROM book WHERE book_id=?", id);
    }

    public void setOwner(int book_id,int person_id) {
        jdbcTemplate.update("UPDATE book set person_id=? WHERE book_id=?",person_id,book_id);
    }

    public Optional<Person> getOwner(int book_id) {
        return jdbcTemplate.query("SELECT person.* FROM person join book ON person.person_id=book.person_id where book_id=?",
                new Object[]{book_id},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public void releaseBook(int book_id) {
        jdbcTemplate.update("UPDATE book set person_id=null WHERE book_id=?",book_id);
    }

}
