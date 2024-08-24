package tjrj.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import tjrj.model.Assunto;

import java.util.List;

public interface AssuntoRepository extends JpaRepository<Assunto, Long> {
    List<Assunto> findByDescricaoContainingIgnoreCase(String descricao);
}

