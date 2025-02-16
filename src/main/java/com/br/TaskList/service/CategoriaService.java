package com.br.TaskList.service;

import com.br.TaskList.dto.CategoriaDTO;
import com.br.TaskList.entities.Categoria;
import com.br.TaskList.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Categoria criarCategoria(CategoriaDTO categoriaDTO) {
        Categoria categoria = new Categoria();
        categoria.setNome(categoriaDTO.nome());

        return categoriaRepository.save(categoria);
    }

    public List<CategoriaDTO> listar(Long id) {
        List<CategoriaDTO> categoriaDTOS = categoriaRepository.findByCategoria(id).stream().map(CategoriaDTO::new).toList();
        return categoriaDTOS;
    }
}
