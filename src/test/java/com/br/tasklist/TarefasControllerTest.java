package com.br.tasklist;

import com.br.tasklist.repository.TarefasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TarefasControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired private TarefasRepository tarefasRepository;
}

