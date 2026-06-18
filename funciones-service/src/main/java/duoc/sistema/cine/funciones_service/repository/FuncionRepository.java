package duoc.sistema.cine.funciones_service.repository;

import duoc.sistema.cine.funciones_service.model.Funcion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FuncionRepository extends JpaRepository<Funcion, Long> {
    List<Funcion> findByIdPelicula(Long idPelicula);
}
