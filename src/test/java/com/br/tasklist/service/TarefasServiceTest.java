package com.br.tasklist.service;

import com.br.tasklist.dto.StatusTarefas;
import com.br.tasklist.dto.TarefasDTO;
import com.br.tasklist.entities.Categoria;
import com.br.tasklist.entities.Tarefas;
import com.br.tasklist.exception.TarefaNaoEncontradaException;
import com.br.tasklist.repository.TarefasRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TarefasServiceTest {

    @Mock
    private TarefasRepository tarefasRepository;

    @InjectMocks
    private TarefasService tarefasService;

    @Test
    void deveCriarTarefaComDadosDoDto() {
        Categoria categoria = categoria(1L, "Estudos");
        TarefasDTO dto = new TarefasDTO(
                null,
                "Estudar testes",
                "Criar testes unitarios",
                StatusTarefas.PENDENTE,
                LocalDate.of(2026, 5, 18),
                LocalDate.of(2026, 5, 18),
                categoria
        );

        Tarefas tarefaSalva = tarefa(10L, dto.titulo(), dto.descricao(), dto.status(),
                dto.dataCriacao(), dto.dataAtualizacao(), categoria);
        when(tarefasRepository.save(any(Tarefas.class))).thenReturn(tarefaSalva);

        Tarefas resultado = tarefasService.criarTarefas(dto);

        ArgumentCaptor<Tarefas> captor = ArgumentCaptor.forClass(Tarefas.class);
        verify(tarefasRepository).save(captor.capture());
        Tarefas tarefaParaSalvar = captor.getValue();

        assertThat(tarefaParaSalvar.getTitulo()).isEqualTo(dto.titulo());
        assertThat(tarefaParaSalvar.getDescricao()).isEqualTo(dto.descricao());
        assertThat(tarefaParaSalvar.getStatus()).isEqualTo(dto.status());
        assertThat(tarefaParaSalvar.getDataCriacao()).isEqualTo(dto.dataCriacao());
        assertThat(tarefaParaSalvar.getDataAtualizacao()).isEqualTo(dto.dataAtualizacao());
        assertThat(tarefaParaSalvar.getCategoria()).isEqualTo(categoria);
        assertThat(resultado).isEqualTo(tarefaSalva);
    }

    @Test
    void deveBuscarTarefaPorId() {
        Tarefas tarefa = tarefa(1L, "Comprar material", "Caderno e caneta",
                StatusTarefas.PENDENTE, LocalDate.of(2026, 5, 18),
                LocalDate.of(2026, 5, 18), categoria(1L, "Pessoal"));
        when(tarefasRepository.findById(1L)).thenReturn(Optional.of(tarefa));

        List<TarefasDTO> resultado = tarefasService.buscaTarefas(1L, null);

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).id()).isEqualTo(1L);
        assertThat(resultado.get(0).titulo()).isEqualTo("Comprar material");
        verify(tarefasRepository, never()).findAll();
        verify(tarefasRepository, never()).findByStatus(any());
    }

    @Test
    void deveBuscarTarefasPorStatusQuandoIdNaoForInformado() {
        Tarefas pendente = tarefa(2L, "Revisar API", "Validar endpoints",
                StatusTarefas.PENDENTE, LocalDate.of(2026, 5, 18),
                LocalDate.of(2026, 5, 18), categoria(2L, "Trabalho"));
        when(tarefasRepository.findByStatus(StatusTarefas.PENDENTE)).thenReturn(List.of(pendente));

        List<TarefasDTO> resultado = tarefasService.buscaTarefas(null, StatusTarefas.PENDENTE);

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).status()).isEqualTo(StatusTarefas.PENDENTE);
        verify(tarefasRepository).findByStatus(StatusTarefas.PENDENTE);
        verify(tarefasRepository, never()).findAll();
    }

    @Test
    void deveLancarExcecaoAoBuscarTarefaInexistentePorId() {
        when(tarefasRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> tarefasService.buscaTarefas(99L, null))
                .isInstanceOf(TarefaNaoEncontradaException.class)
                .hasMessageContaining("99");
    }

    @Test
    void deveAtualizarTarefaExistente() {
        Categoria categoria = categoria(3L, "Projeto");
        Tarefas existente = tarefa(3L, "Titulo antigo", "Descricao antiga",
                StatusTarefas.PENDENTE, LocalDate.of(2026, 5, 17),
                LocalDate.of(2026, 5, 17), categoria);
        TarefasDTO dto = new TarefasDTO(
                3L,
                "Titulo novo",
                "Descricao nova",
                StatusTarefas.CONCLUIDA,
                LocalDate.of(2026, 5, 17),
                LocalDate.of(2026, 5, 17),
                categoria
        );

        when(tarefasRepository.findById(3L)).thenReturn(Optional.of(existente));
        when(tarefasRepository.save(any(Tarefas.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TarefasDTO resultado = tarefasService.atualizarTarefa(3L, dto);

        assertThat(resultado.titulo()).isEqualTo("Titulo novo");
        assertThat(resultado.descricao()).isEqualTo("Descricao nova");
        assertThat(resultado.status()).isEqualTo(StatusTarefas.CONCLUIDA);
        assertThat(resultado.dataAtualizacao()).isEqualTo(LocalDate.now());
        verify(tarefasRepository).save(existente);
    }

    @Test
    void deveLancarExcecaoAoDeletarTarefaInexistente() {
        when(tarefasRepository.existsById(7L)).thenReturn(false);

        assertThatThrownBy(() -> tarefasService.deletartarefa(7L))
                .isInstanceOf(TarefaNaoEncontradaException.class)
                .hasMessageContaining("7");

        verify(tarefasRepository, never()).deleteById(7L);
    }

    private static Categoria categoria(Long id, String nome) {
        Categoria categoria = new Categoria();
        categoria.setId(id);
        categoria.setNome(nome);
        return categoria;
    }

    private static Tarefas tarefa(Long id, String titulo, String descricao, StatusTarefas status,
                                  LocalDate dataCriacao, LocalDate dataAtualizacao, Categoria categoria) {
        Tarefas tarefa = new Tarefas();
        tarefa.setId(id);
        tarefa.setTitulo(titulo);
        tarefa.setDescricao(descricao);
        tarefa.setStatus(status);
        tarefa.setDataCriacao(dataCriacao);
        tarefa.setDataAtualizacao(dataAtualizacao);
        tarefa.setCategoria(categoria);
        return tarefa;
    }
}
