package springApp.Edl.modules;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private int id;


    @NotEmpty(message = "name can't be empty")
    @Size(min = 2, max = 30, message = "name should have 2-30 characters")
    @Pattern(regexp = "[A-Z]\\w+ [A-Z]\\w+ [A-Z]\\w+")
    @Column(name = "full_name")
    private String fullName;

    @Min(value = 1900, message = "are you so old? i think you've already died")
    @Max(value = 2024, message = "are you 0 years old?")
    @Column(name = "year_of_born")
    private int yearOfBorn;

    @OneToMany(mappedBy = "owner",fetch = FetchType.LAZY)
    private List<OneBook> books = new ArrayList<>();


    public Person() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullName;
    }

    public void setFullname(String fullname) {
        this.fullName = fullname;
    }

    public int getYearofborn() {
        return yearOfBorn;
    }

    public void setYearofborn(int yearofborn) {
        this.yearOfBorn = yearofborn;
    }

    public List<OneBook> getBooks() {
        return books;
    }

    public void setBooks(List<OneBook> books) {
        this.books = books;
    }

    public Person(String fullname, int yearofborn) {
        this.fullName = fullname;
        this.yearOfBorn = yearofborn;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", yearOfBorn=" + yearOfBorn +
                '}';
    }
}
