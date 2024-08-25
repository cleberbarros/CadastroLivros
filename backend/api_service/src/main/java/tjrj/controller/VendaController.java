package tjrj.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tjrj.dto.AssuntoDTO;
import tjrj.dto.AutorDTO;
import tjrj.dto.VendaDTO;
import tjrj.model.Venda;
import tjrj.service.AutorService;
import tjrj.service.VendaService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/venda")
@RequiredArgsConstructor
public class VendaController {

    private final VendaService vendaService;


    @GetMapping("/buscar-por-descricao/{descricao}")
    public ResponseEntity<List<VendaDTO>> buscarPorNome(@PathVariable String descricao) {
        List<VendaDTO> vendas = vendaService.buscarPorDescricao(descricao);
        return vendas.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(vendas);
    }

    @PostMapping
    public ResponseEntity<VendaDTO> criarAssunto(@RequestBody VendaDTO vendaDTO) {
        VendaDTO venda = vendaService.criarVenda(vendaDTO);
        return ResponseEntity.ok(venda);
    }

    @GetMapping
    public ResponseEntity<Page<VendaDTO>> buscarTodos(@RequestParam(required = false) String filter,
                                                        @ParameterObject
                                                        @Parameter(description = "Paginação e ordenação",
                                                                schema = @Schema(implementation = Pageable.class,
                                                                        example = "{\"page\": 0, \"size\": 1, \"sort\": [\"descricao,asc\"]}"))
                                                        @PageableDefault Pageable pageable) {

        Page<VendaDTO> vendas = vendaService.buscarComFiltro(filter, pageable);
        return ResponseEntity.ok(vendas);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VendaDTO> atualizarVenda(@PathVariable Long id, @RequestBody VendaDTO vendaDTO) {
        Optional<VendaDTO> vendaAtualizado = vendaService.atualizarVenda(id, vendaDTO);
        return vendaAtualizado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAssunto(@PathVariable Long id) {
        if (vendaService.deletarVenda(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
