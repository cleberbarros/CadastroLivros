package tjrj.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "FormatoVenda")
public class FormatoVenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codVe;

    @Column(nullable = false, length = 20)
    private String descricao;

    @Column(precision = 15, scale = 2)
    private BigDecimal valor;

    @ManyToMany(mappedBy = "formatosVenda")
    private Set<Livro> livros;
}
