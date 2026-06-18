package duoc.sistema.cine.inventario_service.repository;

import duoc.sistema.cine.inventario_service.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventarioRepository extends JpaRepository<Inventario, Long> {
}
