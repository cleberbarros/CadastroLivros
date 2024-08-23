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

import java.util.List;

@RestController
@RequestMapping("/autor")
@RequiredArgsConstructor
public class AutorController {

    private final AutorService autorService;


    @GetMapping
    public ResponseEntity<Page<AutorDTO>> buscarTodos(@ParameterObject @PageableDefault(size = 10) Pageable pageable) {
        Page<AutorDTO> autores = autorService.buscarTodos(pageable);
        return ResponseEntity.ok(autores);
    }

    @GetMapping("/buscar-por-nome/{nome}")
    public ResponseEntity<List<AutorDTO>> buscarPorNome(@PathVariable String nome) {
        List<AutorDTO> autores = autorService.buscarPorNome(nome);
        return autores.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(autores);
    }


}
