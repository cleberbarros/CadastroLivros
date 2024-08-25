package tjrj.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tjrj.model.Autor;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    List<Autor> findByNomeContainingIgnoreCase(String nome);

    @Query("SELECT a FROM Autor a WHERE LOWER(a.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    Page<Autor> findByNomeContaining(@Param("nome") String nome, Pageable pageable);
}

