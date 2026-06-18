package duoc.sistema.cine.entrada_service.config;

import duoc.sistema.cine.entrada_service.model.Entrada;
import duoc.sistema.cine.entrada_service.repository.EntradaRepository;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner cargarDatos(EntradaRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(Entrada.builder()
                                .idUsuario(Long.valueOf(1))
                                .idFuncion(Long.valueOf(1))
                                .cantidad(Long.valueOf(2))
                                .valor(Long.valueOf(10000))
                        .build());

                Faker faker = new Faker();
                for (int i = 0; i < 4; i++) {
                    repository.save(Entrada.builder()
                                    .idUsuario(Long.valueOf(faker.number().numberBetween(1, 5)))
                                    .idFuncion(Long.valueOf(faker.number().numberBetween(1, 5)))
                                    .cantidad(Long.valueOf(faker.number().numberBetween(1, 5)))
                                    .valor(Long.valueOf(faker.number().numberBetween(5000, 20000)))
                            .build());
                }
            }
        };
    }
}
