package gov.nara.um.spring;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Configuration
@ComponentScan({"gov.nara.um.spring.controller.web"})
@EnableWebMvc
@EnableSwagger2
public class UmWebConfig implements WebMvcConfigurer {

    public UmWebConfig() {
        super();
    }

    // swagger configuration
    @Bean
    public Docket swagConfig(){ // @formatter:off
        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())

                .build()
                .pathMapping("/api")
                .directModelSubstitute(LocalDate.class, String.class)
                .genericModelSubstitutes(ResponseEntity.class)
                ;
    }// @formatter:on





    // custom http message converter

    @Override
    public void extendMessageConverters(final List<HttpMessageConverter<?>> converters) {


        final Optional<HttpMessageConverter<?>> converterFound = converters.stream()
                .filter(c -> c instanceof AbstractJackson2HttpMessageConverter)
                .findFirst();


        if (converterFound.isPresent()) {
            final AbstractJackson2HttpMessageConverter converter = (AbstractJackson2HttpMessageConverter) converterFound.get();
            converter.getObjectMapper()
                    .enable(SerializationFeature.INDENT_OUTPUT);
            converter.getObjectMapper()
                    .enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        }
    }
}
