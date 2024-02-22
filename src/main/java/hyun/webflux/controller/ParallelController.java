package hyun.webflux.controller;

import hyun.webflux.service.HelloService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
@RequestMapping("/parallel")
public class ParallelController {

    private final HelloService helloService;

    @GetMapping("/no")
    public Flux<String> noParallel() {
        return Flux.fromIterable(helloService::primeIterator);
    }

    @GetMapping("/yes")
    public Flux<String> yesParallel() {
        return Flux.fromIterable(helloService::primeIteratorParallel);
    }
}
