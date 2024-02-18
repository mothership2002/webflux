package hyun.webflux.test;

import hyun.webflux.vo.Hello;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

//@Component
@RequiredArgsConstructor
public class WebfluxWeb {

    private final WebClient webfluxWebClient;

    public Mono<String> getMessage() {
        return webfluxWebClient.get().uri("/hello").accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Hello.class)
                .map(hello -> hello.getName() + ", " + hello.getMessage());
    }
}
