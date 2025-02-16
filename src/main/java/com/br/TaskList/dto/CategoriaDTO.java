package com.br.TaskList.dto;

import com.br.TaskList.entities.Categoria;

public record CategoriaDTO(Long id, String nome) {

    public CategoriaDTO(Categoria c) {
        this(c.getId(), c.getNome());
    }
}
