package springApp.Edl.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springApp.Edl.modules.Person;
@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {

}
