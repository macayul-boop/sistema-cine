package duoc.sistema.cine.pelicula.service;

import duoc.sistema.cine.pelicula.dto.PeliculaReponse;
import duoc.sistema.cine.pelicula.dto.PeliculaRequest;
import duoc.sistema.cine.pelicula.exception.PeliculaNoEncontradoException;
import duoc.sistema.cine.pelicula.model.Pelicula;
import duoc.sistema.cine.pelicula.repository.PeliculaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PeliculaService {
    private static final Logger log = LoggerFactory.getLogger(PeliculaService.class);

    @Autowired
    private PeliculaRepository peliculaRepository;

    // -------------------------------- METODOS BASICOS --------------------------------

    // Listar todas las peliculas
    public List<Pelicula> listarTodos() {
        log.info("Listando todas las peliculas");
        return peliculaRepository.findAll();
    }

    // Buscar una pelicula por su id
    public Pelicula buscarPeliculaPorId(Long idPelicula) {
        log.info("Buscando pelicula por id: {}",idPelicula);
        Pelicula pelicula = peliculaRepository.findById(idPelicula).orElseThrow(()->new PeliculaNoEncontradoException("Pelicula no encontrada"));
        return pelicula;
    }

    // Guardar una pelicula
    public Pelicula guardarPelicula(PeliculaRequest request) {
        log.info("Creando Pelicula");
        Pelicula pelicula = new Pelicula();
        pelicula.setNombre(request.getNombre());
        pelicula.setDuracion(request.getDuracion());
        pelicula.setSinopsis(request.getSinopsis());
        pelicula.setCategoria(request.getCategoria());
        peliculaRepository.save(pelicula);
        log.info("Guardada pelicula : {}", pelicula);
        return pelicula;
    }

    // Actualizar una pelicula
    public Pelicula actualizarPelicula(Long idPelicula, PeliculaRequest request) {
        log.info("Actualizando pelicula por id: {}",idPelicula);

        Pelicula pelicula = peliculaRepository.findById(idPelicula).orElseThrow(()->new PeliculaNoEncontradoException("Pelicula no encontrada"));

        pelicula.setNombre(request.getNombre());
        pelicula.setDuracion(request.getDuracion());
        pelicula.setSinopsis(request.getSinopsis());
        pelicula.setCategoria(request.getCategoria());

        log.info("Pelicula actualizado correctamente con id: {}", idPelicula);
        peliculaRepository.save(pelicula);
        return pelicula;
    }

    // Eliminar una pelicula
    public void eliminarPelicula(Long idPelicula) {
        log.info("Eliminando pelicula por id: {}",idPelicula);

        Pelicula pelicula = peliculaRepository.findById(idPelicula).orElseThrow(()->new PeliculaNoEncontradoException("Pelicula no encontrada"));

        log.info("Pelicula eliminado correctamente con id: {}", idPelicula);
        peliculaRepository.delete(pelicula);
    }

    // -------------------------------- METODOS EXTRAS --------------------------------

    // Buscar informacion de un pelicula
    public PeliculaReponse obtenerInfoPelicula(Long idPelicula){
        log.info("Obteniendo informacion de la pelicula con id: {}", idPelicula);
        Pelicula pelicula = buscarPeliculaPorId(idPelicula);
        PeliculaReponse respuesta = new PeliculaReponse(pelicula.getNombre(), pelicula.getDuracion());
        return respuesta;
    }

    // Verifica que existe una pelicula
    public Boolean existePelicula(Long idPelicula){
        log.info("Buscando si existe la pelicula con id: {}", idPelicula);
        Boolean existe = peliculaRepository.existsById(idPelicula);
        if(!existe){
            throw new PeliculaNoEncontradoException("Pelicula no encontrada");
        }
        return existe;
    }

}
