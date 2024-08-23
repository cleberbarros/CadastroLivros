package tjrj.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tjrj.dto.AssuntoDTO;
import tjrj.model.Assunto;
import tjrj.repository.AssuntoRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AssuntoService {

    private final AssuntoRepository assuntoRepository;
    private final ModelMapper modelMapper;

    public AssuntoDTO criarAssunto(AssuntoDTO assuntoDto) {
        Assunto assuntoSalvo = assuntoRepository.save(modelMapper.map(assuntoDto, Assunto.class));
        return modelMapper.map(assuntoSalvo, AssuntoDTO.class);
    }

    public Page<AssuntoDTO> buscarTodos(Pageable pageable) {
        return assuntoRepository.findAll(pageable)
                .map(assunto -> modelMapper.map(assunto, AssuntoDTO.class));
    }

    public Optional<AssuntoDTO> buscarPorId(Long id) {
        return assuntoRepository.findById(id).map(assunto -> modelMapper.map(assunto, AssuntoDTO.class));
    }

    public Optional<AssuntoDTO> atualizarAssunto(Long id, AssuntoDTO assuntoDTO) {
        return assuntoRepository.findById(id).map(assuntoExistente -> {
            modelMapper.map(assuntoDTO, assuntoExistente);
            Assunto assuntoAtualizado = assuntoRepository.save(assuntoExistente);
            return modelMapper.map(assuntoAtualizado, AssuntoDTO.class);
        });
    }

    public boolean deletarAssunto(Long id) {
        return assuntoRepository.findById(id).map(assunto -> {
            assuntoRepository.delete(assunto);
            return true;
        }).orElse(false);
    }
}
