package duoc.sistema.cine.confiteria_service.config;

import duoc.sistema.cine.confiteria_service.model.Confiteria;
import duoc.sistema.cine.confiteria_service.repository.ConfiteriaRepository;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner cargarDatos(ConfiteriaRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(Confiteria.builder()
                                .fechaHora(LocalDateTime.of(2026,07,20, 18,00,00))
                                .idProducto(Long.valueOf(1))
                                .idUsuario(Long.valueOf(1))
                                .cantidad(Long.valueOf(1))
                        .build());

                Faker faker = new Faker();
                for (int i = 0; i < 4; i++) {
                    repository.save(Confiteria.builder()
                                    .fechaHora(LocalDateTime.of(2026,07,20, 18,00,00))
                                    .idProducto(Long.valueOf(faker.number().numberBetween(1,5)))
                                    .idUsuario(Long.valueOf(faker.number().numberBetween(1,5)))
                                    .cantidad(Long.valueOf(faker.number().numberBetween(1, 5)))
                            .build());
                }
            }
        };
    }
}
