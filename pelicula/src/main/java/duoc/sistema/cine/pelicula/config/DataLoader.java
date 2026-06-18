package duoc.sistema.cine.pelicula.config;

import duoc.sistema.cine.pelicula.model.Pelicula;
import duoc.sistema.cine.pelicula.repository.PeliculaRepository;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner cargarDatos(PeliculaRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(Pelicula.builder()
                                .nombre("Como entrenar a tu dragon")
                                .duracion(150)
                                .sinopsis("Cinema, no hay palabras para describirla")
                                .categoria("Nose")
                        .build());

                Faker faker = new Faker();
                for (int i = 0; i < 4; i++) {
                    repository.save(Pelicula.builder()
                            .nombre(faker.name().title())
                            .duracion(faker.number().numberBetween(100, 200))
                            .sinopsis(faker.lorem().sentence(10))
                            .categoria(faker.book().genre())
                            .build());
                }
            }
        };
    }
}
