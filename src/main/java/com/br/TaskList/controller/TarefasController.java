package com.br.TaskList.controller;

import com.br.TaskList.dto.StatusTarefas;
import com.br.TaskList.dto.TarefasDTO;
import com.br.TaskList.entities.Tarefas;
import com.br.TaskList.service.TarefasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/tarefas")
public class TarefasController {

    @Autowired
    private TarefasService tarefasService;

    @PostMapping
    public ResponseEntity<Tarefas> post(@RequestBody final TarefasDTO tarefasDTO) {
        Tarefas tarefas = tarefasService.criarTarefas(tarefasDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(tarefas);
    }

    @GetMapping
    public ResponseEntity<List<TarefasDTO>> tarefas(
            @RequestParam(required = false, defaultValue = "0") Long id,
            @RequestParam(required = false) StatusTarefas status) {
        List<TarefasDTO> tarefas = tarefasService.buscaTarefas(id, status);

        if(id != 0 && (tarefas == null || tarefas.isEmpty())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(tarefas);
        }

        return ResponseEntity.status(HttpStatus.OK).body(tarefas);
    }

    @PutMapping
    public ResponseEntity<TarefasDTO> atualizar(
            @RequestParam Long id,
            @RequestBody TarefasDTO tarefasDTO) {

        TarefasDTO tarefaAtualizada = tarefasService.atualizarTarefa(id, tarefasDTO);

        if (tarefaAtualizada == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(tarefaAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deletado = tarefasService.deletartarefa(id);

        if (deletado) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
