package com.br.tasklist.dto;

import com.br.tasklist.entities.Categoria;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Objeto que representa uma categoria")
public record CategoriaDTO(
        @Schema(description = "ID da categoria", example = "1") Long id,
        @Schema(description = "Nome da categoria", example = "Trabalho") String nome
) {
    public CategoriaDTO(Categoria c) {
        this(c.getId(), c.getNome());
    }
}
