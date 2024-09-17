package springApp.Edl.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springApp.Edl.DAO.PeopleDAO;
import springApp.Edl.modules.Person;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private PeopleDAO peopleDAO;
    public PeopleController(PeopleDAO peopleDAO) {
        this.peopleDAO = peopleDAO;
    }

    @GetMapping()
    public String people(Model model) {
        model.addAttribute("peopledao",peopleDAO.show());
        return "people/PeopleShow";
    }

    @GetMapping("/{id}")
    public String people(@PathVariable("id") int id_est_je, Model model) {
        model.addAttribute("ONEperson",peopleDAO.showOne(id_est_je));
        model.addAttribute("booksThatHeHas", peopleDAO.doesPersonHasABook(id_est_je));
        return "people/PersonShow";
    }

    @GetMapping("/new")
    public String newPeople(Model model) {
        model.addAttribute("newPreson", new Person());
        return "people/PersonNew";
    }

    @PostMapping()
    public String PostNewPeople(@ModelAttribute("newPreson") @Valid Person person, BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            return "people/PersonNew";
        }

        peopleDAO.create(person);
        return "redirect:/people";
    }


    @GetMapping("/{id}/edit")
    public String editPeople(@PathVariable("id") int idd, Model model) {
        model.addAttribute("ONEpersonToEdit",peopleDAO.showOne(idd));
        return "people/PersonEdit";
    }

    @PatchMapping("/{id}")
    public String updatePeople(@PathVariable("id") int idToEDIT, @ModelAttribute @Valid Person person,BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()){
            return "people/PersonEdit";
        }
        peopleDAO.update(idToEDIT,person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String deletePeople(@PathVariable("id") int idd) {
        peopleDAO.delete(idd);
        return "redirect:/people";
    }

}
