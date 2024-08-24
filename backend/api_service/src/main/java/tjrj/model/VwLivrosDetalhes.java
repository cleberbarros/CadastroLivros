package tjrj.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "vw_livros_detalhes")
@Data
public class VwLivrosDetalhes {
    @Id
    @Column(name = "livro_id")
    private Long livroId;

    @Column(name = "livro_titulo")
    private String titulo;

    @Column(name = "livro_editora")
    private String editora;

    @Column(name = "livro_edicao")
    private Integer edicao;

    @Column(name = "livro_anopublicacao")
    private String anoPublicacao;

    @Column(name = "autores")
    private String autores;

    @Column(name = "assuntos")
    private String assuntos;

    @Column(name = "vendas")
    private String vendas;
}
