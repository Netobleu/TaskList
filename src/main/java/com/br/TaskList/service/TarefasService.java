package com.br.TaskList.service;

import com.br.TaskList.dto.TarefasDTO;
import com.br.TaskList.entities.Tarefas;
import com.br.TaskList.repository.TarefasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TarefasService {

    @Autowired
    private TarefasRepository tarefasRepository;

        public Tarefas criarTarefas(TarefasDTO tarefasDTO) {
            Tarefas tarefas = new Tarefas();
            tarefas.setTitulo(tarefasDTO.titulo());
            tarefas.setDescricao(tarefasDTO.descricao());
            tarefas.setStatus(tarefasDTO.status());
            tarefas.setDataCriacao(tarefasDTO.dataCriacao());
            tarefas.setDataAtualizacao(tarefasDTO.dataAtualizacao());
            tarefas.setCategoria(tarefasDTO.categoria());

            return tarefasRepository.save(tarefas);
        }
}
