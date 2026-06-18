package duoc.sistema.cine.objetos_perdidos_service.config;


import duoc.sistema.cine.objetos_perdidos_service.model.ObjetoPerdido;
import duoc.sistema.cine.objetos_perdidos_service.repository.ObjetoPerdidoRepository;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner cargarDatos(ObjetoPerdidoRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(ObjetoPerdido.builder()
                                .objeto("Celular")
                                .idFuncion(Long.valueOf(1))
                        .build());

                Faker faker = new Faker();
                for (int i = 0; i < 4; i++) {
                    repository.save(ObjetoPerdido.builder()
                                    .objeto(faker.commerce().productName())
                                    .idFuncion(Long.valueOf(faker.number().numberBetween(1, 5)))
                            .build());
                }
            }
        };
    }
}
