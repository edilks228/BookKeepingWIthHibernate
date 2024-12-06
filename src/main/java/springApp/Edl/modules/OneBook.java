package springApp.Edl.modules;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "book")
public class OneBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private int id;

    @Column(name = "name_of_book")
    private String nameOfBook;

    @Column(name = "name_of_author")
    private String nameOfAuthor;

    @Column(name = "year_of_written")
    private int yearOfWritten;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_of_owner", referencedColumnName = "person_id")
    private Person owner;

    @Column(name = "booked_book_time")
    private LocalDate date;

    @Transient
    private boolean expired;

    public OneBook() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameofbook() {
        return nameOfBook;
    }

    public void setNameofbook(String nameofbook) {
        this.nameOfBook = nameofbook;
    }

    public String getNameofauthor() {
        return nameOfAuthor;
    }

    public void setNameofauthor(String nameofauthor) {
        this.nameOfAuthor = nameofauthor;
    }

    public int getYearofwritten() {
        return yearOfWritten;
    }

    public void setYearofwritten(int yearOfWritten) {
        this.yearOfWritten = yearOfWritten;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public OneBook(String nameofbook, String nameofauthor, int yearofwritten) {
        this.nameOfBook = nameofbook;
        this.nameOfAuthor = nameofauthor;
        this.yearOfWritten = yearofwritten;
    }

    @Override
    public String toString() {
        return "OneBook{" +
                "id=" + id +
                ", nameOfBook='" + nameOfBook + '\'' +
                ", nameOfAuthor='" + nameOfAuthor + '\'' +
                ", yearOfWritten=" + yearOfWritten +
                ", owner=" + owner +
                '}';
    }
}
