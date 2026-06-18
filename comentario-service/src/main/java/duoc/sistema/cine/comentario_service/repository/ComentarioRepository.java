package duoc.sistema.cine.comentario_service.repository;

import duoc.sistema.cine.comentario_service.model.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
}
