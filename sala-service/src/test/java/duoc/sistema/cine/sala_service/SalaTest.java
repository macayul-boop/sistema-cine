package duoc.sistema.cine.sala_service;

import duoc.sistema.cine.sala_service.dto.SalaRequest;
import duoc.sistema.cine.sala_service.model.Sala;
import duoc.sistema.cine.sala_service.repository.SalaRepository;
import duoc.sistema.cine.sala_service.service.SalaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class SalaTest {

    @Mock
    private SalaRepository repository;

    @InjectMocks
    private SalaService service;

    // Leer todas las salas
    @Test
    void listarTodos(){
        when(repository.findAll()).thenReturn(List.of(new Sala(Long.valueOf(1),1,24)));

        List<Sala> lista = service.listarTodos();
        assertNotNull(lista);
        assertEquals(1, lista.size());
    }

    // Leer una sala
    @Test
    void listarPorId(){
        Long id = Long.valueOf(1);
        Sala sala = new Sala(id, 1, 24);
        when(repository.findById(id)).thenReturn(Optional.of(sala));

        Sala salaEncontrada = service.buscarSalaPorId(id);
        assertNotNull(salaEncontrada);
        assertEquals(id, salaEncontrada.getId());
    }

}
