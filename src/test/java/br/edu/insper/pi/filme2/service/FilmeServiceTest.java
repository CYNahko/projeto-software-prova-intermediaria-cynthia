package br.edu.insper.pi.filme2.service;

import br.edu.insper.pi.filme2.exception.FilmeNotFoundException;
import br.edu.insper.pi.filme2.model.Filme;
import br.edu.insper.pi.filme2.repository.FilmeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class FilmeServiceTest {

    @InjectMocks
    private FilmeService filmeService;

    @Mock
    private FilmeRepository filmeRepository;

    @Test
    public void test_listFilmesShouldReturnEmptyList() {
        // mock
        Mockito.when(filmeRepository.findAll()).thenReturn(List.of());

        // chama função
        List<Filme> resp = filmeService.listFilmes();

        // verificações
        Assertions.assertNotNull(resp);
        Assertions.assertTrue(resp.isEmpty());
    }

    @Test
    public void test_listFilmesShouldReturnListWithOneElement() {
        Filme filme = new Filme();
        filme.setId(1);
        filme.setTituloFilme("Matrix");
        filme.setDescricao("Sci-fi clássico");
        filme.setDuracao(136);
        filme.setDiretor("Wachowski");
        filme.setDataCadastro(LocalDate.now());

        // mock
        Mockito.when(filmeRepository.findAll()).thenReturn(List.of(filme));

        // chama função
        List<Filme> resp = filmeService.listFilmes();

        // verificações
        Assertions.assertNotNull(resp);
        Assertions.assertEquals(1, resp.size());
        Assertions.assertEquals("Matrix", resp.get(0).getTituloFilme());
    }

    @Test
    public void test_saveFilmeShouldSetDataCadastro() {
        Filme filme = new Filme();
        filme.setTituloFilme("Inception");
        filme.setDescricao("Um sonho dentro de um sonho");
        filme.setDuracao(148);
        filme.setDiretor("Nolan");

        Filme filmeSalvo = new Filme();
        filmeSalvo.setId(1);
        filmeSalvo.setTituloFilme("Inception");
        filmeSalvo.setDescricao("Um sonho dentro de um sonho");
        filmeSalvo.setDuracao(148);
        filmeSalvo.setDiretor("Nolan");
        filmeSalvo.setDataCadastro(LocalDate.now());

        // mock
        Mockito.when(filmeRepository.save(Mockito.any(Filme.class))).thenReturn(filmeSalvo);

        // chama função
        Filme resp = filmeService.saveFilme(filme);

        // verificações
        Assertions.assertNotNull(resp.getDataCadastro());
        Assertions.assertEquals("Inception", resp.getTituloFilme());
    }

    @Test
    public void test_deleteFilmeShouldDeleteIfExists() {
        Mockito.when(filmeRepository.existsById(1)).thenReturn(true);

        // chama função
        Assertions.assertDoesNotThrow(() -> filmeService.deleteFilme(1));

        // garante que o delete foi chamado
        Mockito.verify(filmeRepository, Mockito.times(1)).deleteById(1);
    }

    @Test
    public void test_deleteFilmeShouldThrowIfNotExists() {
        Mockito.when(filmeRepository.existsById(99)).thenReturn(false);

        // chama função e espera exceção
        Assertions.assertThrows(FilmeNotFoundException.class, () -> filmeService.deleteFilme(99));

        // garante que o delete não foi chamado
        Mockito.verify(filmeRepository, Mockito.never()).deleteById(99);
    }
}
