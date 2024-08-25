package tjrj.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class VendaDTO {

    private Long id;
    private String descricao;
    private BigDecimal valor;
}

