package tjrj.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tjrj.model.Assunto;
import tjrj.model.Livro;

import java.util.List;

public interface AssuntoRepository extends JpaRepository<Assunto, Long> {
    List<Assunto> findByDescricaoContainingIgnoreCase(String descricao);

    @Query("SELECT a FROM Assunto a WHERE LOWER(a.descricao) LIKE LOWER(CONCAT('%', :description, '%'))")
    Page<Assunto> findByDescriptionContaining(@Param("description") String description, Pageable pageable);
}

