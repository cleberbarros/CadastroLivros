package tjrj.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tjrj.model.Autor;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    List<Autor> findByNomeContainingIgnoreCase(String nome);
}

