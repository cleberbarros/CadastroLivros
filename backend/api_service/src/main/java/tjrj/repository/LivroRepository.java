package tjrj.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tjrj.model.Livro;

public interface LivroRepository extends JpaRepository<Livro, Long> {

}

