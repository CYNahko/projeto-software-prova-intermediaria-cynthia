package br.edu.insper.pi.filme2.controller;

import br.edu.insper.pi.filme2.model.Filme;
import br.edu.insper.pi.filme2.service.FilmeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/api/filme"})
public class FilmeController {

    @Autowired
    public FilmeService filmeService;

    @GetMapping
    public List<Filme> getFilmes(){
        return this.filmeService.listFilmes();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Filme createFilme(@RequestBody Filme filme){
        return this.filmeService.saveFilme(filme);
    }

    @DeleteMapping("/{id}")
    public void deleteFilme(@PathVariable Integer id){
        this.filmeService.deleteFilme(id);
    }

}
