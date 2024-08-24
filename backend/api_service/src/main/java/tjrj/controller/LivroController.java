package tjrj.controller;

import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tjrj.dto.LivroDTO;
import tjrj.model.VwLivrosDetalhes;
import tjrj.service.LivroService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/livros")
@RequiredArgsConstructor
public class LivroController {


    private final LivroService livroService;

    @PostMapping
    public ResponseEntity<LivroDTO> criarLivro(@RequestBody LivroDTO livroDTO) {
        LivroDTO livro = livroService.execute(livroDTO);
        return ResponseEntity.ok(livro);
    }

    @GetMapping
    public ResponseEntity<Page<LivroDTO>> buscarTodos(@ParameterObject @PageableDefault(size = 10) Pageable pageable) {
        Page<LivroDTO> livros = livroService.buscarTodos(pageable);
        return ResponseEntity.ok(livros);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroDTO> buscarPorId(@PathVariable Long id) {
        Optional<LivroDTO> livroDTO = livroService.buscarPorId(id);
        return livroDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<LivroDTO> atualizarLivro(@PathVariable Long id, @RequestBody LivroDTO livroDTO) {
        Optional<LivroDTO> livroAtualizado = livroService.atualizarLivro(id, livroDTO);
        return livroAtualizado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarLivro(@PathVariable Long id) {
        if (livroService.deletarLivro(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "livros-detalhados", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> livrosDetalhes(){
        byte[] bytesPdf = this.livroService.livrosDetalhes();

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(bytesPdf);


    }

}
