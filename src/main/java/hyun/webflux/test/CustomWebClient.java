package hyun.webflux.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

//@Configuration
public class CustomWebClient {

    @Bean
    public WebClient webfluxWebClient() {
        return WebClient.builder().baseUrl("http://localhost:8080").build();
    }
}
