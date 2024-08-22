package tjrj.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Assunto")
public class Assunto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codAs;

    @Column(nullable = false, length = 20)
    private String descricao;

    @ManyToMany(mappedBy = "assuntos")
    private Set<Livro> livros;
}
