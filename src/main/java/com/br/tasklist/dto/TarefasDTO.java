package com.br.tasklist.dto;

import com.br.tasklist.entities.Categoria;
import com.br.tasklist.entities.Tarefas;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Schema(description = "Objeto que representa uma tarefa")
public record TarefasDTO(
        @Schema(description = "ID da tarefa (gerado automaticamente)", example = "1")
        Long id,

        @Schema(description = "Título da tarefa", example = "Finalizar documentação")
        @NotBlank(message = "O título é obrigatório")
        @Size(min = 3, max = 100, message = "O título deve ter entre 3 e 100 caracteres")
        String titulo,

        @Schema(description = "Descrição detalhada da tarefa", example = "Finalizar o Swagger até sexta")
        @Size(max = 500, message = "A descrição não pode exceder 500 caracteres")
        String descricao,

        @Schema(description = "Status atual da tarefa", example = "PENDENTE")
        @NotNull(message = "O status é obrigatório")
        StatusTarefas status,

        @Schema(description = "Data de criação da tarefa", example = "2025-10-18")
        @NotNull(message = "A data de criação é obrigatória")
        @PastOrPresent(message = "A data de criação não pode ser futura")
        LocalDate dataCriacao,

        @Schema(description = "Data de atualização da tarefa", example = "2025-10-18")
        @PastOrPresent(message = "A data de atualização não pode ser futura")
        LocalDate dataAtualizacao,

        @Schema(description = "Categoria associada à tarefa")
        @NotNull(message = "A categoria é obrigatória")
        Categoria categoria
) {
    public TarefasDTO(Tarefas t) {
        this(
                t.getId(),
                t.getTitulo(),
                t.getDescricao(),
                t.getStatus(),
                t.getDataCriacao(),
                t.getDataAtualizacao(),
                t.getCategoria()
        );
    }
}