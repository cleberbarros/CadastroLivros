package tjrj.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tjrj.dto.AssuntoDTO;
import tjrj.dto.VendaDTO;
import tjrj.model.Assunto;
import tjrj.model.Venda;
import tjrj.repository.VendaRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VendaService {

    private final VendaRepository vendaRepository;
    private final ModelMapper modelMapper;

    public List<VendaDTO> buscarPorDescricao(String descricao) {
        List<Venda> vendas = vendaRepository.findByDescricaoContainingIgnoreCase(descricao);
        return vendas.stream()
                .map(venda -> modelMapper.map(venda, VendaDTO.class))
                .collect(Collectors.toList());
    }

    public VendaDTO criarVenda(VendaDTO vendaDto) {
        Venda vendaSalvo = vendaRepository.save(modelMapper.map(vendaDto, Venda.class));
        return modelMapper.map(vendaSalvo, VendaDTO.class);
    }

    public Page<VendaDTO> buscarComFiltro(String filter, Pageable pageable) {

        if (filter != null && filter.matches("\\d+")) {
            Long vendaId = Long.parseLong(filter);
            Optional<Venda> venda = vendaRepository.findById(vendaId);
            List<Venda> vendaList = venda.map(Collections::singletonList).orElseGet(Collections::emptyList);

            List<VendaDTO> vendaDTOList = vendaList.stream()
                    .map(vendaItem -> modelMapper.map(vendaItem, VendaDTO.class))
                    .collect(Collectors.toList());

            return new PageImpl<>(vendaDTOList, pageable, vendaDTOList.size());
        } else if (filter != null) {
            Page<Venda> vendas = vendaRepository.findByDescriptionContaining(filter, pageable);
            return vendas.map(venda -> modelMapper.map(venda,VendaDTO.class));
        } else {
            Page<Venda> vendas = vendaRepository.findByDescriptionContaining("", pageable);
            return vendas.map(venda -> modelMapper.map(venda, VendaDTO.class));
        }
    }


    public Optional<VendaDTO> atualizarVenda(Long id, VendaDTO vendaDTO) {
        return vendaRepository.findById(id).map(vendaExistente -> {
            modelMapper.map(vendaDTO, vendaExistente);
            Venda vendaAtualizado = vendaRepository.save(vendaExistente);
            return modelMapper.map(vendaAtualizado, VendaDTO.class);
        });
    }

    public boolean deletarVenda(Long id) {
        return vendaRepository.findById(id).map(assunto -> {
            vendaRepository.delete(assunto);
            return true;
        }).orElse(false);
    }
}
