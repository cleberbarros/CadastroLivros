package tjrj.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import tjrj.model.Venda;

import java.util.List;

public interface VendaRepository extends JpaRepository<Venda, Long> {

    List<Venda> findByDescricaoContainingIgnoreCase(String descricao);
}

