package com.br.tasklist.controller;

import com.br.tasklist.dto.CategoriaDTO;
import com.br.tasklist.entities.Categoria;
import com.br.tasklist.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Categorias", description = "Gerencia categorias de tarefas")
@RestController
@RequestMapping("/api/v1/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @Operation(summary = "Cadastrar nova categoria", description = "Cria uma nova categoria de tarefas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados")
    })
    @PostMapping
    public ResponseEntity<Categoria> cadastrarCategorias(@RequestBody CategoriaDTO categoriaDTO) {
        Categoria categoria = categoriaService.criarCategoria(categoriaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoria);
    }

    @Operation(summary = "Listar categorias", description = "Lista todas categorias ou filtra por ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> listarCategorias(@RequestParam(required = false, defaultValue = "0") Long id) {
        List<CategoriaDTO> categorias = categoriaService.listar(id);

        if (id != 0 && (categorias == null || categorias.isEmpty())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(categorias);
        }
        return ResponseEntity.status(HttpStatus.OK).body(categorias);
    }
}
