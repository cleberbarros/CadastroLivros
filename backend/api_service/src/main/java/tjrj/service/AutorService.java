package tjrj.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tjrj.dto.AutorDTO;
import tjrj.model.Autor;
import tjrj.repository.AutorRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AutorService {

    private final AutorRepository autorRepository;
    private final ModelMapper modelMapper;

    public AutorDTO criarAutor(AutorDTO autorDto) {
        Autor autorSalvo = autorRepository.save(modelMapper.map(autorDto, Autor.class));
        return modelMapper.map(autorSalvo, AutorDTO.class);
    }

    public Page<AutorDTO> buscarTodos(Pageable pageable) {
        return autorRepository.findAll(pageable)
                .map(autor -> modelMapper.map(autor, AutorDTO.class));
    }

    public Optional<AutorDTO> buscarPorId(Long id) {
        return autorRepository.findById(id).map(autor -> modelMapper.map(autor, AutorDTO.class));
    }

    public List<AutorDTO> buscarPorNome(String nome) {
        List<Autor> autores = autorRepository.findByNomeContainingIgnoreCase(nome);
        return autores.stream()
                .map(autor -> modelMapper.map(autor, AutorDTO.class))
                .collect(Collectors.toList());
    }

    public Optional<AutorDTO> atualizarAutor(Long id, AutorDTO autorDTO) {
        return autorRepository.findById(id).map(autorExistente -> {
            modelMapper.map(autorDTO, autorExistente);
            Autor autorAtualizado = autorRepository.save(autorExistente);
            return modelMapper.map(autorAtualizado, AutorDTO.class);
        });
    }

    public boolean deletarAutor(Long id) {
        return autorRepository.findById(id).map(autor -> {
            autorRepository.delete(autor);
            return true;
        }).orElse(false);
    }
}
