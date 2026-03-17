package dev.boog.moneyloverdatamanager.documentation;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("Money Lover Data Manager")
                .description("Money Lover Data Manager api documentation")
                .version("0.1");

        return new OpenAPI().info(info);
    }
}
