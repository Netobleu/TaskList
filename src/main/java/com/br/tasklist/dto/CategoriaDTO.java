package com.br.tasklist.dto;

import com.br.tasklist.entities.Categoria;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Objeto que representa uma categoria")
public record CategoriaDTO(
        @Schema(description = "ID da categoria (gerado automaticamente)", example = "1")
        Long id,

        @Schema(description = "Nome da categoria", example = "Trabalho")
        @NotBlank(message = "O nome da categoria é obrigatório")
        @Size(min = 2, max = 50, message = "O nome deve ter entre 2 e 50 caracteres")
        String nome
) {
    public CategoriaDTO(Categoria c) {
        this(c.getId(), c.getNome());
    }
}