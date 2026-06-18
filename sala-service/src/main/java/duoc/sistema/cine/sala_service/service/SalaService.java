package duoc.sistema.cine.sala_service.service;

import duoc.sistema.cine.sala_service.dto.SalaRequest;
import duoc.sistema.cine.sala_service.exception.SalaNoEncontradoException;
import duoc.sistema.cine.sala_service.model.Sala;
import duoc.sistema.cine.sala_service.repository.SalaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaService {
    private static final Logger log = LoggerFactory.getLogger(SalaService.class);

    @Autowired
    private SalaRepository salaRepository;

    // -------------------------------- METODOS BASICOS --------------------------------

    // Listar todas las salas
    public List<Sala> listarTodos() {
        log.info("Listando todas las salas");
        return salaRepository.findAll();
    }

    // Buscar una sala por su id
    public Sala buscarSalaPorId(Long idSala) {
        log.info("Buscando sala por id: {}", idSala);
        Sala sala = salaRepository.findById(idSala).orElseThrow(() -> new SalaNoEncontradoException("Sala no encontrada"));
        return sala;
    }

    // Guardar una pelicula
    public Sala guardarSala(SalaRequest request) {
        log.info("Creando Sala");
        Sala sala = new Sala();
        sala.setNro_sala(request.getNro_sala());
        sala.setCantidad_asientos(request.getCantidad_asientos());
        salaRepository.save(sala);
        log.info("Guardada sala : {}", sala);
        return sala;
    }

    // Actualizar una sala
    public Sala actualizarSala(Long idSala, SalaRequest request) {
        log.info("Actualizando sala por id: {}", idSala);

        Sala sala = salaRepository.findById(idSala).orElseThrow(() -> new SalaNoEncontradoException("Sala no encontrada"));

        sala.setNro_sala(request.getNro_sala());
        sala.setCantidad_asientos(request.getCantidad_asientos());

        log.info("Sala actualizada correctamente con id: {}", idSala);
        salaRepository.save(sala);
        return sala;
    }

    // Eliminar una sala
    public void eliminarSala(Long idSala) {
        log.info("Eliminando sala por id: {}", idSala);

        Sala sala = salaRepository.findById(idSala).orElseThrow(() -> new SalaNoEncontradoException("Sala no encontrada"));

        log.info("Sala eliminada correctamente con id: {}", idSala);
        salaRepository.delete(sala);
    }



    // -------------------------------- METODOS EXTRAS --------------------------------
    // Verifica que existe una sala
    public Boolean existeSala(Long idSala){
        log.info("Buscando si existe la sala con id: {}", idSala);
        Boolean existe = salaRepository.existsById(idSala);
        if(!existe){
            throw new SalaNoEncontradoException("Sala no encontrada");
        }
        return existe;
    }


}
