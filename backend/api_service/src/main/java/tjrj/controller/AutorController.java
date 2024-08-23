package tjrj.controller;

import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tjrj.dto.AutorDTO;
import tjrj.service.AutorService;

import java.util.Optional;

@RestController
@RequestMapping("/autores")
@RequiredArgsConstructor
public class AutorController {

    private final AutorService autorService;

    @PostMapping
    public ResponseEntity<AutorDTO> criarAutor(@RequestBody AutorDTO autorDTO) {
        AutorDTO autor = autorService.criarAutor(autorDTO);
        return ResponseEntity.ok(autor);
    }

    @GetMapping
    public ResponseEntity<Page<AutorDTO>> buscarTodos(@ParameterObject @PageableDefault(size = 10) Pageable pageable) {
        Page<AutorDTO> autores = autorService.buscarTodos(pageable);
        return ResponseEntity.ok(autores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutorDTO> buscarPorId(@PathVariable Long id) {
        Optional<AutorDTO> autorDTO = autorService.buscarPorId(id);
        return autorDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AutorDTO> atualizarAutor(@PathVariable Long id, @RequestBody AutorDTO autorDTO) {
        Optional<AutorDTO> autorAtualizado = autorService.atualizarAutor(id, autorDTO);
        return autorAtualizado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAutor(@PathVariable Long id) {
        if (autorService.deletarAutor(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
