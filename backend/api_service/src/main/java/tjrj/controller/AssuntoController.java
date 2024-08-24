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
import tjrj.service.AssuntoService;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/assuntos")
@RequiredArgsConstructor
public class AssuntoController {

    private final AssuntoService assuntoService;

    @PostMapping
    public ResponseEntity<AssuntoDTO> criarAssunto(@RequestBody AssuntoDTO assuntoDTO) {
        AssuntoDTO assunto = assuntoService.criarAssunto(assuntoDTO);
        return ResponseEntity.ok(assunto);
    }

    @GetMapping
    public ResponseEntity<Page<AssuntoDTO>> buscarTodos(@RequestParam(required = false) String filter,
                                                      @ParameterObject
                                                      @Parameter(description = "Paginação e ordenação",
                                                              schema = @Schema(implementation = Pageable.class,
                                                                      example = "{\"page\": 0, \"size\": 1, \"sort\": [\"descricao,asc\"]}"))
                                                      @PageableDefault Pageable pageable) {

        Page<AssuntoDTO> assuntos = assuntoService.buscarComFiltro(filter, pageable);
        return ResponseEntity.ok(assuntos);
    }

    @GetMapping("/buscar-por-descricao/{descricao}")
    public ResponseEntity<List<AssuntoDTO>> buscarPorNome(@PathVariable String descricao) {
                List<AssuntoDTO> assuntos = assuntoService.buscarPorDescricao(descricao);
        return assuntos.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(assuntos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssuntoDTO> atualizarAssunto(@PathVariable Long id, @RequestBody AssuntoDTO assuntoDTO) {
        Optional<AssuntoDTO> assuntoAtualizado = assuntoService.atualizarAssunto(id, assuntoDTO);
        return assuntoAtualizado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAssunto(@PathVariable Long id) {
        if (assuntoService.deletarAssunto(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
