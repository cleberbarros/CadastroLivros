package tjrj.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tjrj.model.Venda;

import java.util.List;

public interface VendaRepository extends JpaRepository<Venda, Long> {

    List<Venda> findByDescricaoContainingIgnoreCase(String descricao);

    @Query("SELECT v FROM Venda v WHERE LOWER(v.descricao) LIKE LOWER(CONCAT('%', :description, '%'))")
    Page<Venda> findByDescriptionContaining(@Param("description") String description, Pageable pageable);
}

