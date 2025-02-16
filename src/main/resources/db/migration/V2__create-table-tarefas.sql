CREATE TABLE tarefas (
     id BIGINT AUTO_INCREMENT PRIMARY KEY,
     titulo VARCHAR(255) NOT NULL,
     descricao TEXT,
     status ENUM('PENDENTE', 'EM_ANDAMENTO', 'CONCLUIDA') NOT NULL,
     data_criacao DATE NOT NULL,
     data_atualizacao DATE NOT NULL,
     categoria_id BIGINT,
     FOREIGN KEY (categoria_id) REFERENCES categorias(id)
);