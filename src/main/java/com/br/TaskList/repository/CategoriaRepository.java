package com.br.TaskList.repository;

import com.br.TaskList.entities.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    @Query(nativeQuery = false, value = "SELECT c FROM Categoria c")
    List<Categoria> findByCategoria(@Param("id") Long id);
}
