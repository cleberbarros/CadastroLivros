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
import tjrj.service.AutorService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/autor")
@RequiredArgsConstructor
public class AutorController {

    private final AutorService autorService;


//    @GetMapping
//    public ResponseEntity<Page<AutorDTO>> buscarTodos(@ParameterObject @PageableDefault(size = 10) Pageable pageable) {
//        Page<AutorDTO> autores = autorService.buscarTodos(pageable);
//        return ResponseEntity.ok(autores);
//    }

    @GetMapping("/buscar-por-nome/{nome}")
    public ResponseEntity<List<AutorDTO>> buscarPorNome(@PathVariable String nome) {
        List<AutorDTO> autores = autorService.buscarPorNome(nome);
        return autores.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(autores);
    }

    @PostMapping
    public ResponseEntity<AutorDTO> criar(@RequestBody AutorDTO autorDTO) {
        AutorDTO autor = autorService.criarAssunto(autorDTO);
        return ResponseEntity.ok(autor);
    }

    @GetMapping
    public ResponseEntity<Page<AutorDTO>> buscarTodos(@RequestParam(required = false) String filter,
                                                        @ParameterObject
                                                        @Parameter(description = "Paginação e ordenação",
                                                                schema = @Schema(implementation = Pageable.class,
                                                                        example = "{\"page\": 0, \"size\": 1, \"sort\": [\"descricao,asc\"]}"))
                                                        @PageableDefault Pageable pageable) {

        Page<AutorDTO> autores = autorService.buscarComFiltro(filter, pageable);
        return ResponseEntity.ok(autores);
    }


    @PutMapping("/{id}")
    public ResponseEntity<AutorDTO> atualizar(@PathVariable Long id, @RequestBody AutorDTO autorDTO) {
        Optional<AutorDTO> autorAtualizado = autorService.atualizarAssunto(id, autorDTO);
        return autorAtualizado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAssunto(@PathVariable Long id) {
        if (autorService.deletarAssunto(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
