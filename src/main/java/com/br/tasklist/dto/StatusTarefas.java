package com.br.tasklist.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Status possíveis de uma tarefa")
public enum StatusTarefas {
    @Schema(description = "Tarefa pendente de execução") PENDENTE,
    @Schema(description = "Tarefa em andamento") EM_ANDAMENTO,
    @Schema(description = "Tarefa concluída") CONCLUIDA
}
