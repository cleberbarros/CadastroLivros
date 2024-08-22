package tjrj.dto;

import lombok.Data;
import tjrj.model.Autor;

import java.util.List;
import java.util.Set;

@Data
public class LivroDTO {

    private String titulo;
    private String editora;
    private Integer edicao;
    private String anoPublicacao;
    private List<Autor> autores;

}
