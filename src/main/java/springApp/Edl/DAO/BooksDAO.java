package springApp.Edl.DAO;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BooksDAO {

    private final EntityManager em;
    @Autowired
    public BooksDAO(EntityManager em) {
        this.em = em;
    }
}
