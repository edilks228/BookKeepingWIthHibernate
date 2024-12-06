package springApp.Edl.Repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import springApp.Edl.modules.OneBook;

import java.util.List;


public interface BookRepository extends JpaRepository<OneBook, Integer> {
    public Page<OneBook> findAll(Pageable pageable);

}
