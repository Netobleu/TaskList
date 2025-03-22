package com.br.TaskList.repository;

import com.br.TaskList.dto.StatusTarefas;
import com.br.TaskList.entities.Tarefas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarefasRepository extends JpaRepository<Tarefas, Long> {

    @Query(nativeQuery = false, value = "SELECT t FROM Tarefas t WHERE t.status = :status")
    List<Tarefas> findByStatus(StatusTarefas status);
}
