package tjrj.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tjrj.dto.VendaDTO;
import tjrj.model.Assunto;
import tjrj.model.Venda;
import tjrj.repository.VendaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VendaService {

    private final VendaRepository vendaRepository;
    private final ModelMapper modelMapper;

    public VendaDTO criarAssunto(VendaDTO vendaDto) {
        Venda vendaSalvo = vendaRepository.save(modelMapper.map(vendaDto, Venda.class));
        return modelMapper.map(vendaSalvo, VendaDTO.class);
    }

    public Page<VendaDTO> buscarTodos(Pageable pageable) {
        return vendaRepository.findAll(pageable)
                .map(venda -> modelMapper.map(venda, VendaDTO.class));
    }

    public Optional<VendaDTO> buscarPorId(Long id) {
        return vendaRepository.findById(id).map(venda -> modelMapper.map(venda, VendaDTO.class));
    }

    public List<VendaDTO> buscarPorDescricao(String descricao) {
        List<Venda> vendas = vendaRepository.findByDescricaoContainingIgnoreCase(descricao);
        return vendas.stream()
                .map(autor -> modelMapper.map(vendas, VendaDTO.class))
                .collect(Collectors.toList());
    }

    public Optional<VendaDTO> atualizarAssunto(Long id, VendaDTO vendaDTO) {
        return vendaRepository.findById(id).map(vendaExistente -> {
            modelMapper.map(vendaDTO, vendaExistente);
            Venda vendaAtualizado = vendaRepository.save(vendaExistente);
            return modelMapper.map(vendaAtualizado, VendaDTO.class);
        });
    }

    public boolean deletarAssunto(Long id) {
        return vendaRepository.findById(id).map(venda -> {
            vendaRepository.delete(venda);
            return true;
        }).orElse(false);
    }
}
