package springApp.Edl.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springApp.Edl.Repositories.BookRepository;
import springApp.Edl.modules.OneBook;
import springApp.Edl.modules.Person;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;
    private final PersonService personService;

    @Autowired
    public BookService(BookRepository bookRepository, PersonService personService) {
        this.bookRepository = bookRepository;
        this.personService = personService;
    }

    @Transactional
    public List<OneBook> findAll(Pageable pageable) {
        return bookRepository.findAll();
    }


    @Transactional
    public List<OneBook> findAll(Boolean doSort) {
        List<OneBook> BookList = bookRepository.findAll();
        if(doSort) {
            BookList.sort(Comparator.comparingInt(OneBook::getYearofwritten));

        }
        return BookList;
    }

    @Transactional
    public OneBook findById(int id) {
        OneBook book =  bookRepository.findById(id).get();
        LocalDate dateNow = LocalDate.now();
        LocalDate bookedDate = book.getDate();
        if (bookedDate!=null) {
            long dayesBetween = ChronoUnit.DAYS.between(bookedDate, dateNow);
            if (dayesBetween > 10) {
                book.setExpired(true);
            }
        }
        return book;
    }




    @Transactional
    public OneBook save(OneBook book) {
        return bookRepository.save(book);
    }

    @Transactional
    public  void delete(int id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    public void update(int id, OneBook oneBook) {
        oneBook.setId(id);
        bookRepository.save(oneBook);
    }


    @Transactional
    public Person findThatPerson(int idOfBook) {
        OneBook oneBook = bookRepository.findById(idOfBook).get();
        Person person = oneBook.getOwner();
        System.out.println(person);
        if(person != null) {
            return person;
        }
        return null;
    }

    @Transactional
    public void giveBookTo(int personId, int bookId){
        Person person = personService.findById(personId);
        OneBook book = bookRepository.findById(bookId).get();
        LocalDate date = LocalDate.now();
        book.setDate(date);
        book.setExpired(false);
        book.setOwner(person);
        person.getBooks().add(book);
    }



    @Transactional
    public void makeFree(int bookId){
        OneBook book = bookRepository.findById(bookId).get();
        Person person = book.getOwner();
        if (person.getBooks().contains(book)){
            person.getBooks().remove(book);
        }
        book.setOwner(null);
        book.setDate(null);
    }

    @Transactional
    public List<OneBook> searchByName(String name) {
        List<OneBook> allBooks = bookRepository.findAll();
        List<OneBook> searchedBooks = new ArrayList<>();
        for (OneBook book : allBooks) {
            String curName = book.getNameofbook();
            boolean doesItMatch = true;
            if(curName.length()<name.length()) {
                for (int i = 0; i < curName.length(); i++) {
                    if (curName.charAt(i) != name.charAt(i)) {
                        doesItMatch = false;
                        break;
                    }
                }
            }
            else {
                for (int i = 0; i < name.length(); i++) {
                    if (curName.charAt(i) != name.charAt(i)) {
                        doesItMatch = false;
                        break;
                    }
                }
            }
            if (doesItMatch) {
                searchedBooks.add(book);
            }
        }
        return searchedBooks;
    }
}
