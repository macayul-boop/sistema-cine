package duoc.sistema.cine.entrada_service.repository;

import duoc.sistema.cine.entrada_service.model.Entrada;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntradaRepository extends JpaRepository<Entrada, Long> {
}
