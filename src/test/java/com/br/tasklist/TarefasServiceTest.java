package com.br.tasklist;

import com.br.tasklist.repository.TarefasRepository;
import com.br.tasklist.service.TarefasService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TarefasServiceTest {
    @Mock
    private TarefasRepository tarefasRepository;
    @InjectMocks
    private TarefasService tarefasService;
}

