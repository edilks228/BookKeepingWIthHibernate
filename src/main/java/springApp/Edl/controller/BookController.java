package springApp.Edl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import springApp.Edl.DAO.BooksDAO;
import springApp.Edl.DAO.PeopleDAO;
import springApp.Edl.modules.OneBook;
import springApp.Edl.modules.Person;

import java.util.List;

@Controller
@RequestMapping("/Books")
public class BookController {

    private final BooksDAO booksDAO;
    private final PeopleDAO peopleDAO;

    public BookController(BooksDAO booksDAO, PeopleDAO peopleDAO) {
        this.booksDAO = booksDAO;
        this.peopleDAO = peopleDAO;
    }

    @GetMapping()
    public String books(Model model) {
        model.addAttribute("oneBookjj", booksDAO.index());
        return "books/index";
    }

    @GetMapping("/{id}")
    public String ShowOne(@PathVariable("id") int id_of_book, Model model) {
        model.addAttribute("oneBookShow", booksDAO.ShowOneBook(id_of_book));
        model.addAttribute("Person", booksDAO.findthatPerson(id_of_book));
        model.addAttribute("people", peopleDAO.show());
        model.addAttribute("EmptyPerson", new Person()); // Инициализируем EmptyPerson
        return "books/ShowOneBook";
    }
    @PatchMapping("/{id}/add")
    public String giveBookToPerson(@PathVariable("id") int id_of_book, @RequestParam("personId") int personId, RedirectAttributes redirectAttributes) {
        System.out.println("Assigning book ID " + id_of_book + " to person ID " + personId);
        booksDAO.giveBookTo(personId, id_of_book);
        redirectAttributes.addAttribute("id", id_of_book);
        return "redirect:/Books/{id}";
    }

    @PostMapping("/{id}/makefree")
    public String ShowMakeFree(@PathVariable("id") int id_of_book, Model model, RedirectAttributes redirectAttributes) {
        booksDAO.makeFree(id_of_book);
        redirectAttributes.addAttribute("id",id_of_book);
        return "redirect:/Books/{id}";
    }


    @GetMapping("/new")
    public String CreateNew(@ModelAttribute OneBook oneBook) {
        return "books/CreateNew";
    }
    @PostMapping()
    public String CreateNew(@ModelAttribute("oneBook") OneBook oneBook, Model model) {
        booksDAO.save(oneBook);
        return "redirect:/Books";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id,Model model) {
        model.addAttribute("oneBook", booksDAO.ShowOneBook(id));
        return "books/editForm";
    }

    @PatchMapping("/{id}")
    public String updateBook(@PathVariable("id") int id, @ModelAttribute OneBook oneBook, Model model) {
        booksDAO.update(id,oneBook);
        return "redirect:/Books";
    }

    @DeleteMapping("/{id}")
    public String DeleteOne(@PathVariable("id") int id) {
        booksDAO.delete(id);
        return "redirect:/Books";
    }


}
