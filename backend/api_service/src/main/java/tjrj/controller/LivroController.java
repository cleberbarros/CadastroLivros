package tjrj.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tjrj.dto.LivroDTO;
import tjrj.service.LivroService;

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
    public ResponseEntity<Page<LivroDTO>> buscarTodos(@RequestParam(required = false) String filter,
                                                      @ParameterObject
                                                      @Parameter(description = "Paginação e ordenação",
                                                              schema = @Schema(implementation = Pageable.class,
                                                                      example = "{\"page\": 0, \"size\": 1, \"sort\": [\"titulo,asc\"]}"))
                                                      @PageableDefault Pageable pageable){

        Page<LivroDTO> livros = livroService.buscarComFiltro(filter, pageable);
        return ResponseEntity.ok(livros);

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
