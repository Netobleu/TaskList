package com.br.tasklist.service;

import com.br.tasklist.dto.CategoriaDTO;
import com.br.tasklist.entities.Categoria;
import com.br.tasklist.exception.CategoriaNaoEncontradaException;
import com.br.tasklist.repository.CategoriaRepository;
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
        if (id == null || id == 0) {
            return categoriaRepository.findAll()
                    .stream()
                    .map(CategoriaDTO::new)
                    .toList();
        }

        return categoriaRepository.findById(id)
                .map(categoria -> List.of(new CategoriaDTO(categoria)))
                .orElseThrow(() -> new CategoriaNaoEncontradaException(id));
    }

    public CategoriaDTO atualizarCategoria(Long id, CategoriaDTO categoriaDTO) {
        return categoriaRepository.findById(id)
                .map(categoria -> {
                    categoria.setNome(categoriaDTO.nome());
                    Categoria salva = categoriaRepository.save(categoria);
                    return new CategoriaDTO(salva);
                })
                .orElseThrow(() -> new CategoriaNaoEncontradaException(id));
    }

    public void deletarCategoria(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new CategoriaNaoEncontradaException(id);
        }
        categoriaRepository.deleteById(id);
    }
}
