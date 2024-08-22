package tjrj.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Livro")
@Data
@NoArgsConstructor
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codl")
    private Long id;

    @Column(nullable = false, length = 40)
    private String titulo;

    @Column(nullable = false, length = 40)
    private String editora;

    @Column(nullable = false)
    private int edicao;

    @Column(nullable = false, length = 4)
    private String anopublicacao;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "Livro_Autor",
            joinColumns = @JoinColumn(name = "livro_codl"),
            inverseJoinColumns = @JoinColumn(name = "autor_codau")
    )
    private List<Autor> autores = new ArrayList<>();



}
