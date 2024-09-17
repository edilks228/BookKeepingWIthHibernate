package springApp.Edl.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import springApp.Edl.modules.OneBook;
import springApp.Edl.modules.Person;

import java.util.List;

@Component
public class BooksDAO {
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public BooksDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<OneBook> index(){
        return jdbcTemplate.query("select * from booktable", new BeanPropertyRowMapper<>(OneBook.class));
    }


    public OneBook ShowOneBook(int id){
        return jdbcTemplate.query("select * from booktable where id = ?",new Object[]{id}, new BeanPropertyRowMapper<>(OneBook.class)).stream().findAny().orElse(null);
    }


    public void save(OneBook oneBook){
        jdbcTemplate.update("INSERT INTO booktable(nameofbook,nameofauthor,yearofwritten) VALUES(?,?,?)",oneBook.getNameofbook(), oneBook.getNameofauthor(), oneBook.getYearofwritten());
    }

    public void delete(int id){
        jdbcTemplate.update("DELETE FROM booktable WHERE id = ?",id);
    }


    public void update(int id, OneBook booktochange){
        jdbcTemplate.update("UPDATE booktable SET nameofbook=?, nameofauthor=?,yearofwritten=? WHERE id=?",
                booktochange.getNameofbook(),booktochange.getNameofauthor(),booktochange.getYearofwritten(),booktochange.getId());
    }



    public Person findthatPerson( Integer id){
        try {
            Integer personId = jdbcTemplate.queryForObject("SELECT personid FROM booktable WHERE id = ?", Integer.class, id);
            if (personId != null) {
                return jdbcTemplate.queryForObject("SELECT * FROM persontable WHERE id = ?", new BeanPropertyRowMapper<>(Person.class), personId);
            }
            return null;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void makeFree(int idOfBook){
        jdbcTemplate.update("UPDATE booktable SET personid = NULL where id=?",idOfBook);
    }

    public void giveBookTo(int idOfPerson,int idOfBook){
        System.out.println("person: " + idOfPerson + " book: " + idOfBook);
        jdbcTemplate.update("UPDATE booktable SET personid = ? where id=?",idOfPerson,idOfBook);
    }

}
