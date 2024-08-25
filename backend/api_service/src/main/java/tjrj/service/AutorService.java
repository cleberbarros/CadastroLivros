package tjrj.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tjrj.dto.AutorDTO;
import tjrj.model.Autor;
import tjrj.repository.AutorRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AutorService {

    private final AutorRepository autorRepository;
    private final ModelMapper modelMapper;



    public AutorDTO criarAssunto(AutorDTO autorDto) {
        Autor autorSalvo = autorRepository.save(modelMapper.map(autorDto, Autor.class));
        return modelMapper.map(autorSalvo, AutorDTO.class);
    }

    public Page<AutorDTO> buscarComFiltro(String filter, Pageable pageable) {

        if (filter != null && filter.matches("\\d+")) {
            Long autorId = Long.parseLong(filter);
            Optional<Autor> autor = autorRepository.findById(autorId);
            List<Autor> autorList = autor.map(Collections::singletonList).orElseGet(Collections::emptyList);

            List<AutorDTO> autorDTOList = autorList.stream()
                    .map(autorItem -> modelMapper.map(autorItem, AutorDTO.class))
                    .collect(Collectors.toList());

            return new PageImpl<>(autorDTOList, pageable, autorDTOList.size());
        } else if (filter != null) {
            Page<Autor> autores = autorRepository.findByNomeContaining(filter, pageable);
            return autores.map(autor -> modelMapper.map(autor, AutorDTO.class));
        } else {
            Page<Autor> autores = autorRepository.findByNomeContaining("", pageable);
            return autores.map(autor -> modelMapper.map(autor, AutorDTO.class));
        }
    }


    public List<AutorDTO> buscarPorNome(String nome) {
        List<Autor> autores = autorRepository.findByNomeContainingIgnoreCase(nome);
        return autores.stream()
                .map(autor -> modelMapper.map(autor, AutorDTO.class))
                .collect(Collectors.toList());
    }

    public Optional<AutorDTO> atualizarAssunto(Long id, AutorDTO autorDTO) {
        return autorRepository.findById(id).map(autorExistente -> {
            modelMapper.map(autorDTO, autorExistente);
            Autor assuntoAtualizado = autorRepository.save(autorExistente);
            return modelMapper.map(assuntoAtualizado, AutorDTO.class);
        });
    }

    public boolean deletarAssunto(Long id) {
        return autorRepository.findById(id).map(autor -> {
            autorRepository.delete(autor);
            return true;
        }).orElse(false);
    }
}
