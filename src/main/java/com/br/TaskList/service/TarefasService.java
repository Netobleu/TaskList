package com.br.TaskList.service;

import com.br.TaskList.dto.StatusTarefas;
import com.br.TaskList.dto.TarefasDTO;
import com.br.TaskList.entities.Tarefas;
import com.br.TaskList.repository.TarefasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.List;

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

        public List<TarefasDTO> buscaTarefas(Long id, StatusTarefas status) {
            if (id != 0) {
                return tarefasRepository.findById(id)
                        .map(tarefas -> List.of(new TarefasDTO(tarefas)))
                        .orElse(null);
            } else if (status != null) {
                return tarefasRepository.findByStatus(status).stream()
                        .map(TarefasDTO::new)
                        .toList();
            } else {
                return tarefasRepository.findAll().stream()
                        .map(TarefasDTO::new)
                        .toList();
            }
        }

    public TarefasDTO atualizarTarefa(Long id, TarefasDTO tarefasDTO) {
        return tarefasRepository.findById(id)
                .map(tarefas -> {
                    tarefas.setTitulo(tarefasDTO.titulo());
                    tarefas.setDescricao(tarefasDTO.descricao());
                    tarefas.setStatus(tarefasDTO.status());
                    tarefas.setDataCriacao(tarefasDTO.dataCriacao());
                    tarefas.setDataAtualizacao(LocalDate.now());
                    tarefas.setCategoria(tarefasDTO.categoria());

                    Tarefas tarefasSalvas = tarefasRepository.save(tarefas);
                    return new TarefasDTO(tarefasSalvas);
                })
                .orElse(null);
    }

    public boolean deletartarefa(Long id) {
            if (tarefasRepository.existsById(id)) {
                tarefasRepository.deleteById(id);
                return true;
            }
            return false;
    }
}
