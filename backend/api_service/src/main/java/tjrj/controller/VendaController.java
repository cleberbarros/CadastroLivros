package tjrj.controller;

import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tjrj.dto.AutorDTO;
import tjrj.dto.VendaDTO;
import tjrj.model.Venda;
import tjrj.service.AutorService;
import tjrj.service.VendaService;

import java.util.List;

@RestController
@RequestMapping("/venda")
@RequiredArgsConstructor
public class VendaController {

    private final VendaService vendaService;


    @GetMapping
    public ResponseEntity<Page<VendaDTO>> buscarTodos(@ParameterObject @PageableDefault(size = 10) Pageable pageable) {
        Page<VendaDTO> autores = vendaService.buscarTodos(pageable);
        return ResponseEntity.ok(autores);
    }

    @GetMapping("/buscar-por-descricao/{descricao}")
    public ResponseEntity<List<VendaDTO>> buscarPorNome(@PathVariable String descricao) {
        List<VendaDTO> vendas = vendaService.buscarPorDescricao(descricao);
        return vendas.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(vendas);
    }


}
