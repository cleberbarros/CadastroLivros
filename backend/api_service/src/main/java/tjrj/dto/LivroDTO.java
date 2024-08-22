package tjrj.dto;

import lombok.Data;
import java.util.Set;

@Data
public class LivroDTO {

    private Long codl;
    private String titulo;
    private String editora;
    private Integer edicao;
    private String anoPublicacao;
    private Set<Long> autores;
    private Set<Long> assuntos;
    private Set<Long> formatosVenda;
}
