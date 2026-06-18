package duoc.sistema.cine.comentario_service.config;

import duoc.sistema.cine.comentario_service.model.Comentario;
import duoc.sistema.cine.comentario_service.repository.ComentarioRepository;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner cargarDatos(ComentarioRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(Comentario.builder()
                                .fechaHora(LocalDateTime.of(2026,06,13, 18,00,00))
                                .comentario("La pelicula estuvo god lol")
                                .idPelicula(Long.valueOf(1))
                                .idusuario(Long.valueOf(1))
                        .build());

                Faker faker = new Faker();
                for (int i = 0; i < 4; i++) {
                    repository.save(Comentario.builder()
                                    .fechaHora(LocalDateTime.of(2026,06,13, 18,00,00))
                                    .comentario(faker.lorem().sentence(10))
                                    .idPelicula(Long.valueOf(faker.number().numberBetween(1, 5)))
                                    .idusuario(Long.valueOf(faker.number().numberBetween(1, 5)))
                            .build());
                }
            }
        };
    }
}
