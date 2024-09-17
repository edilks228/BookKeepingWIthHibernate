package springApp.Edl.modules;



public class OneBook {
    private int id;
    private String nameofbook;
    private String nameofauthor;
    private int yearofwritten;

    public OneBook() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameofbook() {
        return nameofbook;
    }

    public void setNameofbook(String nameofbook) {
        this.nameofbook = nameofbook;
    }

    public String getNameofauthor() {
        return nameofauthor;
    }

    public void setNameofauthor(String nameofauthor) {
        this.nameofauthor = nameofauthor;
    }

    public int getYearofwritten() {
        return yearofwritten;
    }

    public void setYearofwritten(int yearOfWritten) {
        this.yearofwritten = yearOfWritten;
    }

    public OneBook(int id, String nameofbook, String nameofauthor, int yearofwritten) {
        this.id = id;
        this.nameofbook = nameofbook;
        this.nameofauthor = nameofauthor;
        this.yearofwritten = yearofwritten;
    }
}
