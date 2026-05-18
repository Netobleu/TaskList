package com.br.tasklist.service;

import com.br.tasklist.dto.CategoriaDTO;
import com.br.tasklist.entities.Categoria;
import com.br.tasklist.exception.CategoriaNaoEncontradaException;
import com.br.tasklist.repository.CategoriaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaService categoriaService;

    @Test
    void deveCriarCategoriaComNomeDoDto() {
        CategoriaDTO dto = new CategoriaDTO(null, "Trabalho");
        Categoria categoriaSalva = categoria(1L, "Trabalho");
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoriaSalva);

        Categoria resultado = categoriaService.criarCategoria(dto);

        ArgumentCaptor<Categoria> captor = ArgumentCaptor.forClass(Categoria.class);
        verify(categoriaRepository).save(captor.capture());

        assertThat(captor.getValue().getNome()).isEqualTo("Trabalho");
        assertThat(resultado).isEqualTo(categoriaSalva);
    }

    @Test
    void deveListarTodasCategoriasQuandoIdNaoForInformado() {
        when(categoriaRepository.findAll()).thenReturn(List.of(
                categoria(1L, "Trabalho"),
                categoria(2L, "Estudos")
        ));

        List<CategoriaDTO> resultado = categoriaService.listar(null);

        assertThat(resultado)
                .extracting(CategoriaDTO::nome)
                .containsExactly("Trabalho", "Estudos");
        verify(categoriaRepository, never()).findById(any());
    }

    @Test
    void deveListarCategoriaPorId() {
        when(categoriaRepository.findById(2L)).thenReturn(Optional.of(categoria(2L, "Estudos")));

        List<CategoriaDTO> resultado = categoriaService.listar(2L);

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).id()).isEqualTo(2L);
        assertThat(resultado.get(0).nome()).isEqualTo("Estudos");
        verify(categoriaRepository, never()).findAll();
    }

    @Test
    void deveLancarExcecaoAoListarCategoriaInexistente() {
        when(categoriaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoriaService.listar(99L))
                .isInstanceOf(CategoriaNaoEncontradaException.class)
                .hasMessageContaining("99");
    }

    @Test
    void deveAtualizarCategoriaExistente() {
        Categoria existente = categoria(4L, "Pessoal");
        when(categoriaRepository.findById(4L)).thenReturn(Optional.of(existente));
        when(categoriaRepository.save(any(Categoria.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CategoriaDTO resultado = categoriaService.atualizarCategoria(4L, new CategoriaDTO(4L, "Casa"));

        assertThat(resultado.id()).isEqualTo(4L);
        assertThat(resultado.nome()).isEqualTo("Casa");
        verify(categoriaRepository).save(existente);
    }

    @Test
    void deveDeletarCategoriaExistente() {
        when(categoriaRepository.existsById(5L)).thenReturn(true);

        categoriaService.deletarCategoria(5L);

        verify(categoriaRepository).deleteById(5L);
    }

    @Test
    void deveLancarExcecaoAoDeletarCategoriaInexistente() {
        when(categoriaRepository.existsById(6L)).thenReturn(false);

        assertThatThrownBy(() -> categoriaService.deletarCategoria(6L))
                .isInstanceOf(CategoriaNaoEncontradaException.class)
                .hasMessageContaining("6");

        verify(categoriaRepository, never()).deleteById(6L);
    }

    private static Categoria categoria(Long id, String nome) {
        Categoria categoria = new Categoria();
        categoria.setId(id);
        categoria.setNome(nome);
        return categoria;
    }
}
