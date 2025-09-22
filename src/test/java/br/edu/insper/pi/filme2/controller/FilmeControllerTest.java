package br.edu.insper.pi.filme2.controller;

import br.edu.insper.pi.filme2.model.Filme;
import br.edu.insper.pi.filme2.service.FilmeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class FilmeControllerTest {

    @InjectMocks
    private FilmeController filmeController;

    @Mock
    private FilmeService filmeService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(filmeController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void test_listFilmesShouldReturnOneFilme() throws Exception {
        Filme filme = new Filme();
        filme.setId(1);
        filme.setTituloFilme("Matrix");
        filme.setDescricao("Sci-fi clássico");
        filme.setDuracao(136);
        filme.setDiretor("Wachowski");
        filme.setDataCadastro(LocalDate.now());

        Mockito.when(filmeService.listFilmes()).thenReturn(List.of(filme));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/filme"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].tituloFilme").value("Matrix"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].diretor").value("Wachowski"));
    }

    @Test
    public void test_createFilmeShouldReturnCreatedFilme() throws Exception {
        Filme filmeRequest = new Filme();
        filmeRequest.setTituloFilme("Inception");
        filmeRequest.setDescricao("Um sonho dentro de um sonho");
        filmeRequest.setDuracao(148);
        filmeRequest.setDiretor("Nolan");

        Filme filmeResponse = new Filme();
        filmeResponse.setId(1);
        filmeResponse.setTituloFilme("Inception");
        filmeResponse.setDescricao("Um sonho dentro de um sonho");
        filmeResponse.setDuracao(148);
        filmeResponse.setDiretor("Nolan");
        filmeResponse.setDataCadastro(LocalDate.now());

        Mockito.when(filmeService.saveFilme(Mockito.any(Filme.class))).thenReturn(filmeResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/filme")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(filmeRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk()) // ou isCreated() se o controller tiver @ResponseStatus(HttpStatus.CREATED)
                .andExpect(MockMvcResultMatchers.jsonPath("$.tituloFilme").value("Inception"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.diretor").value("Nolan"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dataCadastro").exists());
    }

    @Test
    public void test_deleteFilmeShouldReturnOk() throws Exception {
        Mockito.doNothing().when(filmeService).deleteFilme(1);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/filme/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(filmeService, Mockito.times(1)).deleteFilme(1);
    }
}
