package duoc.sistema.cine.inventario_service.config;

import duoc.sistema.cine.inventario_service.model.Inventario;
import duoc.sistema.cine.inventario_service.repository.InventarioRepository;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner cargarDatos(InventarioRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(Inventario.builder()
                                .nombreProducto("Chocolate")
                                .cantidad(Long.valueOf(20))
                                .valor(Long.valueOf(2500))
                        .build());

                Faker faker = new Faker();
                for (int i = 0; i < 4; i++) {
                    repository.save(Inventario.builder()
                            .nombreProducto(faker.name().title())
                            .cantidad(Long.valueOf(faker.number().numberBetween(10, 20)))
                            .valor(Long.valueOf(faker.number().numberBetween(2500, 10000)))
                            .build());
                }
            }
        };
    }
}
