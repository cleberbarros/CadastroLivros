package tjrj.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tjrj.dto.LivroDTO;
import tjrj.model.Livro;
import tjrj.repository.LivroRepository;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;
    private final ModelMapper modelMapper;

    public LivroDTO execute(LivroDTO livroDto) {
        Livro livroSalvo = livroRepository.save(modelMapper.map(livroDto, Livro.class));
        return modelMapper.map(livroSalvo, LivroDTO.class);
    }

    public Page<LivroDTO> buscarTodos(Pageable pageable) {
        return livroRepository.findAll(pageable)
                .map(livro -> modelMapper.map(livro, LivroDTO.class));
    }

    public Optional<LivroDTO> buscarPorId(Long id) {
        return livroRepository.findById(id).map(livro -> modelMapper.map(livro, LivroDTO.class));
    }

    public Optional<LivroDTO> atualizarLivro(Long id, LivroDTO livroDTO) {
        return livroRepository.findById(id).map(livroExistente -> {
            modelMapper.map(livroDTO, livroExistente);
            Livro livroAtualizado = livroRepository.save(livroExistente);
            return modelMapper.map(livroAtualizado, LivroDTO.class);
        });
    }

    public boolean deletarLivro(Long id) {
        return livroRepository.findById(id).map(livro -> {
            livroRepository.delete(livro);
            return true;
        }).orElse(false);
    }
}
