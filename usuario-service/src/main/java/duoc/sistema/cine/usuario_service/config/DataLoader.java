package duoc.sistema.cine.usuario_service.config;

import duoc.sistema.cine.usuario_service.model.Usuario;
import duoc.sistema.cine.usuario_service.repository.UsuarioRepository;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner cargarDatos(UsuarioRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(Usuario.builder()
                                .username("user1")
                                .password("123456")
                                .nombre("Juan")
                                .apellido("pedro")
                                .fechaNacimiento(LocalDate.of(2006,11,16))
                                .email("juanpedro1@gmail.com")
                                .tipoUsuario("empleado")
                        .build());

                Faker faker = new Faker();
                for (int i = 0; i < 4; i++) {
                    repository.save(Usuario.builder()
                                    .username(faker.name().username())
                                    .password("123456")
                                    .nombre(faker.name().firstName())
                                    .apellido(faker.name().lastName())
                                    .fechaNacimiento(LocalDate.now().minusYears(faker.number().numberBetween(18, 60)))
                                    .email(faker.internet().emailAddress())
                                    .tipoUsuario(faker.options().option("empleado", "cliente"))
                            .build());
                }
            }
        };
    }
}
