package com.br.TaskList.controller;

import com.br.TaskList.dto.TarefasDTO;
import com.br.TaskList.entities.Tarefas;
import com.br.TaskList.service.TarefasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
