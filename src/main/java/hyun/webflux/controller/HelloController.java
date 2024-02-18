package hyun.webflux.controller;

import hyun.webflux.vo.Hello;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@RestController
public class HelloController {

    @GetMapping("/hello/{name}")
    public Mono<String> hello(@PathVariable String name) {
        Hello hello = new Hello("Hello", name);
        return Mono.just(hello.getMessage() + " !, " + hello.getName());
    }

    @GetMapping("/flux/test/{count}")
    public Flux<String> count(@PathVariable Long count) {
        return Flux.fromIterable(LongStream.range(0, count).boxed().map(Object::toString).collect(Collectors.toSet()));
    }

    @GetMapping(value = "/stream", produces = "text/event-stream")
    public Flux<String> stream() {
        return Flux
                .interval(Duration.ofMillis(300))
                .map(seq -> "Flux sequence: " + seq);
    }
}
