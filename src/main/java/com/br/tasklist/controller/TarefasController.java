package com.br.tasklist.controller;

import com.br.tasklist.dto.StatusTarefas;
import com.br.tasklist.dto.TarefasDTO;
import com.br.tasklist.entities.Tarefas;
import com.br.tasklist.service.TarefasService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "Tarefas", description = "Gerencia tarefas da aplicação")
@RestController
@RequestMapping("/api/v1/tarefas")
public class TarefasController {

    @Autowired
    private TarefasService tarefasService;

    @Operation(summary = "Criar nova tarefa", description = "Cria uma nova tarefa associada a uma categoria.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tarefa criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação")
    })
    @PostMapping
    public ResponseEntity<Tarefas> post(@RequestBody final TarefasDTO tarefasDTO) {
        Tarefas tarefas = tarefasService.criarTarefas(tarefasDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(tarefas);
    }

    @Operation(summary = "Listar tarefas", description = "Lista todas tarefas ou filtra por ID e status.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefas retornadas com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhuma tarefa encontrada")
    })
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

    @Operation(summary = "Atualizar tarefa", description = "Atualiza os dados de uma tarefa existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")
    })
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

    @Operation(summary = "Deletar tarefa", description = "Remove uma tarefa pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tarefa deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deletado = tarefasService.deletartarefa(id);

        if (deletado) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
