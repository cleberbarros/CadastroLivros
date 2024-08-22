package tjrj.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class FormatoVendaDTO {

    private Long codVe;
    private String descricao;
    private BigDecimal valor;
}
