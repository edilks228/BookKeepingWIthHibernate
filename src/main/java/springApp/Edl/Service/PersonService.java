package springApp.Edl.Service;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springApp.Edl.Repositories.PeopleRepository;
import springApp.Edl.modules.OneBook;
import springApp.Edl.modules.Person;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class PersonService {
    private final PeopleRepository peopleRepository;


    @Autowired
    public PersonService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Transactional
    public List<Person> findAll() {
        return peopleRepository.findAll();
    }
    @Transactional
    public Person findById(int id) {
        return peopleRepository.findById(id).get();
    }
    @Transactional
    public Person save(Person person) {
        return peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person personToUpdate) {
        personToUpdate.setId(id);
        peopleRepository.save(personToUpdate);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }

    @Transactional
    public List<OneBook> doesPersonHasABook(int id){
        Person person = peopleRepository.findById(id).get();
        List<OneBook> books = person.getBooks();
        for (OneBook book : books) {
            checkIfIsExpired(book);
        }
        return books;
    }

    @Transactional
    public void checkIfIsExpired(OneBook book) {
        LocalDate dateNow = LocalDate.now();
        LocalDate bookedDate = book.getDate();
        if (bookedDate!=null) {
            long dayesBetween = ChronoUnit.DAYS.between(bookedDate, dateNow);
            if (dayesBetween > 10) {
                book.setExpired(true);
            }
        }
    }
}
