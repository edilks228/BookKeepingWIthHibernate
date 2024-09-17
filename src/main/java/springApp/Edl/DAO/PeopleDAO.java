package springApp.Edl.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import springApp.Edl.modules.OneBook;
import springApp.Edl.modules.Person;

import java.util.List;

@Component
public class PeopleDAO {
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public PeopleDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> show(){
        return jdbcTemplate.query("select * from persontable", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person showOne(int id){
        return jdbcTemplate.query("SELECT * FROM persontable WHERE id=?",new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).stream().findFirst().orElse(null);
    }


    public void create(Person person){
        jdbcTemplate.update("INSERT INTO persontable(fullname,yearofborn) values(?,?)",person.getFullname(),person.getYearofborn());
    }

    public void update(int id,Person editperson ){
        jdbcTemplate.update("UPDATE persontable SET fullname=?, yearofborn=? WHERE id=?",editperson.getFullname(),editperson.getYearofborn(),id);
    }

    public void delete(int id){
        jdbcTemplate.update("DELETE FROM persontable WHERE id=?",id);
    }


    public List<OneBook> doesPersonHasABook(int id){
        try {
            return jdbcTemplate.query("select * from booktable WHERE personid=?", new BeanPropertyRowMapper<>(OneBook.class),id);
        } catch (EmptyResultDataAccessException e) {
            return List.of();
        }
    }


}
