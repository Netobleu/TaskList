package com.br.TaskList.dto;

import com.br.TaskList.entities.Categoria;
import com.br.TaskList.entities.Tarefas;
import org.hibernate.engine.spi.Status;

import java.time.LocalDate;

public record TarefasDTO(
        Long id,
        String titulo,
        String descricao,
        StatusTarefas status,
        LocalDate dataCriacao,
        LocalDate dataAtualizacao,
        Categoria categoria) {

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
