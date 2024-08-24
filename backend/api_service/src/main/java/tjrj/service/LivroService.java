package tjrj.service;

import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tjrj.dto.LivroDTO;
import tjrj.exception.ReportException;
import tjrj.exception.ResourceNotFoundException;
import tjrj.model.Assunto;
import tjrj.model.Autor;
import tjrj.model.Livro;
import tjrj.model.Venda;
import tjrj.repository.LivroRepository;
import tjrj.repository.VwLivrosDetalhesRepository;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;
    private final VwLivrosDetalhesRepository vwLivrosDetalhesRepository;
    private final ModelMapper modelMapper;

    public LivroDTO execute(LivroDTO livroDto) {
        Livro livroSalvo = livroRepository.save(modelMapper.map(livroDto, Livro.class));
        return modelMapper.map(livroSalvo, LivroDTO.class);
    }
    public Page<LivroDTO> buscarComFiltro(String filter, Pageable pageable) {

        if (filter != null && filter.matches("\\d+")) {
            Long livroId = Long.parseLong(filter);
            Optional<Livro> livro = livroRepository.findById(livroId);
            List<Livro> livroList = livro.map(Collections::singletonList).orElseGet(Collections::emptyList);

            List<LivroDTO> livroDTOList = livroList.stream()
                    .map(livroItem -> modelMapper.map(livroItem, LivroDTO.class))
                    .collect(Collectors.toList());

            return new PageImpl<>(livroDTOList, pageable, livroDTOList.size());
        } else if (filter != null) {
            Page<Livro> livros = livroRepository.findByDescriptionContaining(filter, pageable);
            return livros.map(livro -> modelMapper.map(livro, LivroDTO.class));
        } else {
            Page<Livro> livros = livroRepository.findByDescriptionContaining("", pageable);
            return livros.map(livro -> modelMapper.map(livro, LivroDTO.class));
        }
    }


    public Optional<LivroDTO> atualizarLivro(Long id, LivroDTO livroDTO) {
        return livroRepository.findById(id).map(livroExistente -> {
            modelMapper.map(livroDTO, livroExistente);

            // Atualizar a lista de autores
            livroExistente.getAutores().clear();
            livroExistente.getAutores().addAll(livroDTO.getAutores().stream()
                    .map(autorDTO -> modelMapper.map(autorDTO, Autor.class))
                    .collect(Collectors.toList()));

            // Atualizar a lista de assuntos
            livroExistente.getAssuntos().clear();
            livroExistente.getAssuntos().addAll(livroDTO.getAssuntos().stream()
                    .map(assuntoDTO -> modelMapper.map(assuntoDTO, Assunto.class))
                    .collect(Collectors.toList()));

            // Atualizar a lista de formatos de venda
            livroExistente.getFormatosVendas().clear();
            livroExistente.getFormatosVendas().addAll(livroDTO.getFormatosVendas().stream()
                    .map(vendaDTO -> modelMapper.map(vendaDTO, Venda.class))
                    .collect(Collectors.toList()));

            Livro livroAtualizado = livroRepository.save(livroExistente);
            return modelMapper.map(livroAtualizado, LivroDTO.class);
        });
    }

//    public boolean deletarLivro(Long id) {
//        return livroRepository.findById(id).map(livro -> {
//            livroRepository.delete(livro);
//            return true;
//        }).orElse(false);
//    }
public boolean deletarLivro(Long id) {
    Livro livro = livroRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Livro com ID " + id + " não encontrado."));
    livroRepository.delete(livro);
    return true;
}

    public byte[] livrosDetalhes()  {
        try {
            var inpustStream = this.getClass().getResourceAsStream("/relatorios/livros.jasper");
            var livrosDetalhes = this.vwLivrosDetalhesRepository.findAll();
            var dataSource = new JRBeanCollectionDataSource(livrosDetalhes);
            var parametros = new HashMap<String, Object>();
            parametros.put("REPORT_LOCALE", new Locale("pt","BR"));
            var jasperPrint = JasperFillManager.fillReport(inpustStream,parametros,dataSource);
            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (JRException e) {
            throw new ReportException("Erro ao processar o relatório de livros. Tente novamente ou contate o suporte.", e);
        } catch (Exception e) {
            throw new ReportException("Não foi possivel emitir relatório de livros",e);
        }
    }

}
