package ru.karam.project.conrtollers;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.karam.project.dao.PersonDao;
import ru.karam.project.model.Person;
import ru.karam.project.util.PersonValidator;


@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonDao personDao;
    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(PersonDao personDao, PersonValidator personValidator) {
        this.personDao = personDao;
        this.personValidator = personValidator;
    }

  @GetMapping()
    public String people(Model model) {
        model.addAttribute("people", personDao.getAll());
        return "people/getAll";
  }

    @GetMapping("/{id}")
    public String people(@PathVariable("id") Integer id, Model model) {
        //Person person = personDao.getById(id);
        model.addAttribute("books", personDao.getCurrentBook(id));
        model.addAttribute("person", personDao.getById(id));
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person, Model model) {
        return "people/newPerson";
    }


    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {return "people/newPerson";}

        personDao.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String peopleEdit(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("person", personDao.getById(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult,
                         @PathVariable("id") Integer id) {

        if (bindingResult.hasErrors()) {return "people/edit";}
        personDao.update(id,person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Integer id) {
        personDao.delete(id);
        return "redirect:/people";
    }


}
