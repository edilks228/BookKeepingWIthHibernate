package springApp.Edl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import springApp.Edl.Repositories.BookRepository;
import springApp.Edl.Service.BookService;
import springApp.Edl.Service.PersonService;
import springApp.Edl.modules.OneBook;
import springApp.Edl.modules.Person;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/Books")
public class BookController {

    private final BookService bookService;
    private final PersonService personService;
    private final BookRepository bookRepository;

    @Autowired
    public BookController(BookService bookService, PersonService personService, BookRepository bookRepository) {
        this.bookService = bookService;
        this.personService = personService;
        this.bookRepository = bookRepository;

    }


    @GetMapping()
    public String books(@RequestParam(value = "page", required = false) Integer pages,
                        @RequestParam(value = "size", required = false) Integer size,
                        @RequestParam(value = "doSort", defaultValue = "false") Boolean doSort,
                        Model model) {


        if (pages != null && size != null) {
            Pageable pageable;
            if (doSort) {
                pageable = PageRequest.of(pages, size, Sort.by("yearOfWritten").ascending());
            } else {
                pageable = PageRequest.of(pages, size);
            }

            Page<OneBook> books = bookRepository.findAll(pageable);

            model.addAttribute("books", books.getContent());
            model.addAttribute("currentPage", books.getNumber());
            model.addAttribute("totalPages", books.getTotalPages());
            model.addAttribute("totalElements", books.getTotalElements());
            model.addAttribute("size",size);

            return "books/pageAbleIndex";
        } else {
            List<OneBook> books = bookRepository.findAll();
            if (doSort) {
            }
            model.addAttribute("BooksList", bookService.findAll(doSort));
            return "books/index";
        }
    }



    @GetMapping("/{id}")
    public String ShowOne(@PathVariable("id") int id_of_book, Model model) {
        model.addAttribute("oneBookShow", bookService.findById(id_of_book));
        model.addAttribute("person", bookService.findThatPerson(id_of_book));
        model.addAttribute("people", personService.findAll());
        model.addAttribute("EmptyPerson", new Person()); // Инициализируем EmptyPerson
        return "books/ShowOneBook";
    }




    @PatchMapping("/{id}/add")
    public String giveBookToPerson(@PathVariable("id") int id_of_book, @RequestParam("personId") int personId, RedirectAttributes redirectAttributes) {
        System.out.println("Assigning book ID " + id_of_book + " to person ID " + personId);
        bookService.giveBookTo(personId, id_of_book);
        redirectAttributes.addAttribute("id", id_of_book);
        return "redirect:/Books/{id}";
    }



    @PostMapping("/{id}/makefree")
    public String ShowMakeFree(@PathVariable("id") int id_of_book, Model model, RedirectAttributes redirectAttributes) {
        bookService.makeFree(id_of_book);
        redirectAttributes.addAttribute("id",id_of_book);
        return "redirect:/Books/{id}";
    }


    @GetMapping("/new")
    public String CreateNew(Model model) {
        model.addAttribute("newOneBook", new OneBook());
        return "books/CreateNew";
    }

    @PostMapping()
    public String CreateNew(@ModelAttribute("newOneBook") OneBook oneBook, Model model) {
        bookService.save(oneBook);
        return "redirect:/Books";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id,Model model) {
        model.addAttribute("oneBook", bookService.findById(id));
        return "books/editForm";
    }

    @PatchMapping("/{id}")
    public String updateBook(@PathVariable("id") int id, @ModelAttribute OneBook oneBook, Model model) {
        bookService.update(id,oneBook);
        return "redirect:/Books";
    }

    @DeleteMapping("/{id}")
    public String DeleteOne(@PathVariable("id") int id) {
        bookService.delete(id);
        return "redirect:/Books";
    }

    @GetMapping("/search")
    public String Search(Model model) {
        model.addAttribute("foundBooks", new ArrayList<OneBook>());
        return "books/search";
    }

    @PostMapping("/search")
    public String Search(@RequestParam("searchBook") String nameOfSearchingBook, Model model) {
        List<OneBook> foundBooks = bookService.searchByName(nameOfSearchingBook);
        model.addAttribute("foundBooks", foundBooks);
        return "books/search";
    }

}
