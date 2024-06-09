package ru.karam.project.conrtollers;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.karam.project.dao.BookDao;
import ru.karam.project.dao.PersonDao;
import ru.karam.project.model.Book;
import ru.karam.project.model.Person;

import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookDao bookDao;
    private final PersonDao personDao;

    @Autowired
    public BookController(BookDao bookDao, PersonDao personDao) {
        this.bookDao = bookDao;
        this.personDao = personDao;
    }

    @GetMapping()
    public String books(Model model) {
        model.addAttribute("books", bookDao.getBooks());
        return "books/allBooks";
    }
    @GetMapping("/{id}")
    public String book(@PathVariable("id") Integer id, Model model,@ModelAttribute("person") Person person) {
        //Book book = bookDao.getBook(id);
        model.addAttribute("book", bookDao.getBook(id));
        Optional<Person> bookOwner = bookDao.getOwner(id);
        if (bookOwner.isPresent()) {
            model.addAttribute("owner", bookOwner.get());
        }else {
            model.addAttribute("people",personDao.getAll());
        }
        return "books/Book";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book, Model model) {
        return "books/newBook";
    }

    @PostMapping()
    public String addBook(@ModelAttribute("book") @Valid Book book,
                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/newBook";
        }
        bookDao.addBook(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String editBook(@PathVariable("id") Integer id, Model model){
        model.addAttribute("book", bookDao.getBook(id));
        return "books/editBook";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult,
                         @PathVariable("id") Integer id){
        if (bindingResult.hasErrors()) {
            return "books/editBook";
        }
        bookDao.updateBook(id,book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Integer id) {
        bookDao.deleteBook(id);
        return "redirect:/books";
    }
    @PatchMapping("/{id}/setuser")
    public String book_test(@PathVariable("id") Integer book_id,
                            @ModelAttribute("person") Person person) {
        bookDao.setOwner(book_id,person.getPerson_id());
        return "redirect:/books";
    }

    @PatchMapping("/{id}/releaseBook")
    public String releaseBook(@PathVariable("id") Integer Book_id){
        bookDao.releaseBook(Book_id);
        return "redirect:/books/" + Book_id;
    }

}
