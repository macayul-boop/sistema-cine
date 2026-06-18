package duoc.sistema.cine.pelicula.repository;

import duoc.sistema.cine.pelicula.model.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeliculaRepository extends JpaRepository<Pelicula, Long> {
}
