package springApp.Edl.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springApp.Edl.DAO.PersonDAO;
import springApp.Edl.Service.PersonService;
import springApp.Edl.modules.Person;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private PersonService personService;


    @Autowired
    public PeopleController(PersonService personService, PersonDAO personDAO) {
        this.personService = personService;

    }

    @GetMapping()
    public String people(Model model) {
        model.addAttribute("peopledao",personService.findAll());
        return "people/PeopleShow";
    }

    @GetMapping("/{id}")
    public String people(@PathVariable("id") int id_est_je, Model model) {
        model.addAttribute("ONEperson",personService.findById(id_est_je));
        model.addAttribute("booksThatHeHas", personService.doesPersonHasABook(id_est_je));
        return "people/PersonShow";
    }

    @GetMapping("/new")
    public String newPeople(Model model) {
        model.addAttribute("newPerson", new Person());
        return "people/PersonNew";
    }

    @PostMapping()
    public String PostNewPeople(@ModelAttribute("newPerson") @Valid Person person, BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            return "people/PersonNew";
        }

        personService.save(person);
        return "redirect:/people";
    }


    @GetMapping("/{id}/edit")
    public String editPeople(@PathVariable("id") int idd, Model model) {
        model.addAttribute("ONEpersonToEdit",personService.findById(idd));
        return "people/PersonEdit";
    }

    @PatchMapping("/{id}")
    public String updatePeople(@PathVariable("id") int idToEDIT, @ModelAttribute @Valid Person person,BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()){
            return "people/PersonEdit";
        }
        personService.update(idToEDIT,person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String deletePeople(@PathVariable("id") int idd) {
        personService.delete(idd);
        return "redirect:/people";
    }

}
