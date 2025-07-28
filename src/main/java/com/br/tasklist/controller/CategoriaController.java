package com.br.tasklist.controller;

import com.br.tasklist.dto.CategoriaDTO;
import com.br.tasklist.entities.Categoria;
import com.br.tasklist.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @PostMapping
    public ResponseEntity<Categoria> cadastrarCategorias(@RequestBody CategoriaDTO categoriaDTO) {
        Categoria categoria = categoriaService.criarCategoria(categoriaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoria);
    }

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> listarCategorias(@RequestParam("id") Long id) {
        List<CategoriaDTO> categorias = categoriaService.listar(id);

        if (id != 0 && (categorias == null || categorias.isEmpty())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(categorias);
        }
        return ResponseEntity.status(HttpStatus.OK).body(categorias);
    }
}
