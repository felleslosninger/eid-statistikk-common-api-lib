package no.difi.statistics.query.config;

import no.difi.statistics.query.api.QueryRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.ZonedDateTime;

import static java.lang.String.format;
import static springfox.documentation.builders.PathSelectors.any;
import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;

@Configuration
@EnableAutoConfiguration
@EnableSwagger2
public class AppConfig {

    @Autowired
    private BackendConfig backendConfig;

    @Bean
    public QueryRestController api() {
        return new QueryRestController(backendConfig.queryService());
    }

    @Bean
    public Docket apiDocumentation() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("statistikk-utdata")
                .directModelSubstitute(ZonedDateTime.class, java.util.Date.class)
                .select()
                    .apis(basePackage(QueryRestController.class.getPackage().getName()))
                    .paths(any())
                    .build()
                .apiInfo(new ApiInfoBuilder()
                        .title("Statistikk for offentlige tjenester")
                        .description(
                                format(
                                        "Beskrivelse av API for uthenting av data (versjon %s).",
                                        System.getProperty("difi.version", "N/A")
                                )
                        )
                        .build()
                );
    }

}
