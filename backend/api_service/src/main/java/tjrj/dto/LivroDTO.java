package tjrj.dto;

import lombok.Data;
import tjrj.model.Assunto;
import tjrj.model.Autor;
import tjrj.model.Venda;

import java.util.List;

@Data
public class LivroDTO {

    private Long id;
    private String titulo;
    private String editora;
    private Integer edicao;
    private String anoPublicacao;
    private List<Autor> autores;
    private List<Assunto> assuntos;
    private List<Venda>formatosVendas;

}
