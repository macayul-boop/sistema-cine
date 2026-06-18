package duoc.sistema.cine.sala_service.config;

import duoc.sistema.cine.sala_service.model.Sala;
import duoc.sistema.cine.sala_service.repository.SalaRepository;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner cargarDatos(SalaRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(Sala.builder()
                        .nro_sala(1)
                        .cantidad_asientos(150)
                        .build());

                Faker faker = new Faker();
                for (int i = 0; i < 4; i++) {
                    repository.save(Sala.builder()
                            .nro_sala(faker.number().numberBetween(1, 20))
                            .cantidad_asientos(faker.number().numberBetween(100, 200))
                            .build());
                }
            }
        };
    }
}
