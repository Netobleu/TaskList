package com.br.tasklist.dto;

import com.br.tasklist.entities.Categoria;
import com.br.tasklist.entities.Tarefas;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "Objeto que representa uma tarefa")
public record TarefasDTO(
        @Schema(description = "ID da tarefa", example = "1") Long id,
        @Schema(description = "Título da tarefa", example = "Finalizar documentação") String titulo,
        @Schema(description = "Descrição detalhada da tarefa", example = "Finalizar o Swagger profissional até sexta-feira") String descricao,
        @Schema(description = "Status atual da tarefa", example = "PENDENTE") StatusTarefas status,
        @Schema(description = "Data de criação da tarefa", example = "2025-10-18") LocalDate dataCriacao,
        @Schema(description = "Data de atualização da tarefa", example = "2025-10-18") LocalDate dataAtualizacao,
        @Schema(description = "Categoria associada à tarefa") Categoria categoria
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
