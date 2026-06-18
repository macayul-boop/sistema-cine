package duoc.sistema.cine.sala_service.repository;

import duoc.sistema.cine.sala_service.model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaRepository extends JpaRepository<Sala, Long> {
}
