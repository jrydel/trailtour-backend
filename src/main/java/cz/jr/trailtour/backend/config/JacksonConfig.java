package cz.jr.trailtour.backend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Created by Jiří Rýdel on 2/28/20, 11:11 AM
 */
@Configuration
public class JacksonConfig {

    @Bean
    @Primary
    public ObjectMapper configureObjectMapper() {
        return new ObjectMapper().registerModule(new JavaTimeModule());
    }
}
