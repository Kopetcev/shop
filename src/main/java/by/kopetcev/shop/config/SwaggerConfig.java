package by.kopetcev.shop.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI shopOpenAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Shop Open Api")
                                .version("1.0.0")
                                .contact(
                                        new Contact()
                                                .email("kopetcev@gmail.com")
                                                .name("Evgeny Kopetc")
                                )
                );
    }
}

