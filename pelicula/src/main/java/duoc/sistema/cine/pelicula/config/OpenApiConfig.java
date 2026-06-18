package duoc.sistema.cine.pelicula.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI().info(new Info()
                .title("Pelicula Service")
                .version("1.0.0")
                .description("Microservicio para gestión las peliculas del cine."));
    }
}
