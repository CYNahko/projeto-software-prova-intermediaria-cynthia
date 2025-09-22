package br.edu.insper.pi.filme2.exception;

public class FilmeNotFoundException extends RuntimeException {
    public FilmeNotFoundException(String message) {
        super(message);
    }
}
