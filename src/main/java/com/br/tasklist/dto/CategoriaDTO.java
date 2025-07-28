package com.br.tasklist.dto;

import com.br.tasklist.entities.Categoria;

public record CategoriaDTO(Long id, String nome) {

    public CategoriaDTO(Categoria c) {
        this(c.getId(), c.getNome());
    }
}
