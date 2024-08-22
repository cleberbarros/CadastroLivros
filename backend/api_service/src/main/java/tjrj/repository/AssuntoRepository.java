package tjrj.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import tjrj.model.Assunto;

public interface AssuntoRepository extends JpaRepository<Assunto, Long> {
}

