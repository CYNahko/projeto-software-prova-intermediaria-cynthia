package br.edu.insper.pi.filme2.service;

import br.edu.insper.pi.filme2.exception.FilmeNotFoundException;
import br.edu.insper.pi.filme2.model.Filme;
import br.edu.insper.pi.filme2.repository.FilmeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FilmeService {

    @Autowired
    public FilmeRepository filmeRepository;

    //lista
    public List<Filme> listFilmes(){
        return filmeRepository.findAll();
    }

    //cadastra
    public Filme saveFilme(Filme filme){
        filme.setDataCadastro(LocalDate.now());
        filme = (Filme)this.filmeRepository.save(filme);
        return filme;

    }

    //exclui
    public void deleteFilme(Integer id){
        if (!filmeRepository.existsById(id)){
            throw new FilmeNotFoundException("Filme não encontrado");
        }
        filmeRepository.deleteById(id);
    }
}
