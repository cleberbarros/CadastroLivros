package tjrj.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "Autor")
@Data
@NoArgsConstructor
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codau")
    private Long id;

    @Column(nullable = false, length = 40)
    private String nome;

    @ManyToMany(mappedBy = "autores")
    private Set<Livro> livros;

}
