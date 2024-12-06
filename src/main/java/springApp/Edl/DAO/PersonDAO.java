package springApp.Edl.DAO;

import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import springApp.Edl.Repositories.PeopleRepository;
import springApp.Edl.modules.OneBook;
import springApp.Edl.modules.Person;

import java.util.List;

@Component
public class PersonDAO {
    private final PeopleRepository peopleRepository;
    private EntityManager entityManager;

    @Autowired
    public PersonDAO(PeopleRepository peopleRepository, EntityManager entityManager) {
        this.peopleRepository = peopleRepository;
        this.entityManager = entityManager;
    }

}
