package duoc.sistema.cine.funciones_service.config;

import duoc.sistema.cine.funciones_service.model.Funcion;
import duoc.sistema.cine.funciones_service.repository.FuncionRepository;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner cargarDatos(FuncionRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(Funcion.builder()
                                .fechaHoraInicio(LocalDateTime.of(2026,07,20, 18,00,00)) //"2026-05-21T18:00:00"
                                .fechaHoraTermino(LocalDateTime.of(2026,07,20, 20,00,00))
                                .idPelicula(Long.valueOf(1))
                                .idSala(Long.valueOf(1))
                        .build());

                Faker faker = new Faker();
                for (int i = 0; i < 4; i++) {
                    repository.save(Funcion.builder()
                                .fechaHoraInicio(LocalDateTime.of(2026,07,20, 18,00,00)) //"2026-05-21T18:00:00"
                                .fechaHoraTermino(LocalDateTime.of(2026,07,20, 20,00,00))
                                .idPelicula(Long.valueOf(faker.number().numberBetween(1, 5)))
                                .idSala(Long.valueOf(faker.number().numberBetween(1, 5)))
                            .build());
                }
            }
        };
    }
}
