package springApp.Edl.modules;

import jakarta.validation.constraints.*;

public class Person {
    private int id;


    @NotEmpty(message = "name can't be empty")
    @Size(min = 2, max = 30, message = "name should have 2-30 characters")
    @Pattern(regexp = "[A-Z]\\w+ [A-Z]\\w+ [A-Z]\\w+")
    private String fullname;

    @Min(value = 1900, message = "are you so old? i think you've already died")
    @Max(value = 2024, message = "are you 0 years old?")
    private int yearofborn;


    public Person() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public int getYearofborn() {
        return yearofborn;
    }

    public void setYearofborn(int yearofborn) {
        this.yearofborn = yearofborn;
    }

    public Person(int id, String fullname, int yearofborn) {
        this.id = id;
        this.fullname = fullname;
        this.yearofborn = yearofborn;
    }
}
