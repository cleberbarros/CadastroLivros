package tjrj.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tjrj.model.Autor;

public interface AutorRepository extends JpaRepository<Autor, Long> {
}

