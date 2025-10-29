package com.br.tasklist.exception;

public class CategoriaNaoEncontradaException extends RuntimeException {

    public CategoriaNaoEncontradaException(Long id) {
        super("Categoria com ID " + id + " não encontrada.");
    }
}
